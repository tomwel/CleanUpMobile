package br.com.clean_up_mobile.activity;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;
import android.content.SharedPreferences;

public class LoginActivity extends Activity {

	public static final String MyPREFERENCES = "MyPrefs";
	public static final String email = "emailKey";
	public static final String pass = "passwordKey";

	SharedPreferences sharedpreferences;
	Gson gson = new Gson();
	UsuarioDB db;
	ProgressBar progress;
	TextView txtMensagem;
	TextView errorMsg;
	EditText emailET;
	EditText pwdET;
	Usuario usuario;
	ProgressDialog prgDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new UsuarioDB(getApplicationContext());
		setContentView(R.layout.activity_login);

		emailET = (EditText) findViewById(R.id.loginEmail);
		pwdET = (EditText) findViewById(R.id.loginPassword);

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(btnLoginOnClickListener);

		Button btnRegistro = (Button) findViewById(R.id.btnRegistro);
		btnRegistro.setOnClickListener(btnRegistroOnClickListener);
	}

	private OnClickListener btnRegistroOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent cadastroIntent = new Intent(getApplicationContext(),
					CadastroActivity.class);
			cadastroIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(cadastroIntent);
		}
	};

	private OnClickListener btnLoginOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			validaForm();
		}
	};

	public void validaForm() {

		String email = emailET.getText().toString();
		String password = pwdET.getText().toString();

		if (Util.isNotNull(email) && Util.isNotNull(password)) {
			if (Util.validate(email)) {

				Usuario u = new Usuario();
				u.setEmail(email);
				u.setSenha(password);

				doLogin(u);
			} else {
				Util.criarToast(getApplicationContext(),
						R.string.msgEmailInvalido);
			}
		} else {
			Util.criarToast(getApplicationContext(),
					R.string.msgFormularioVazio);
		}
	}

	public void doLogin(Usuario usuario) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_LOGIN, usuario).execute();
	}

	public void navigatetoHome() {
		Intent homeIntent = new Intent(getApplicationContext(),
				HomeActivity.class);
		homeIntent.putExtra("usuario", usuario);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Usuario u;
		private String url;

		public HttpAsyncTask(String url, Usuario u) {
			this.u = u;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
			prgDialog = new ProgressDialog(LoginActivity.this);
			prgDialog.setMessage("Aguarde, realizando o login. ");
			prgDialog.setTitle("Login");
			prgDialog.setCancelable(true);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			prgDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, u);
		}

		@Override
		protected void onPostExecute(String result) {
			try {

				if (prgDialog.isShowing()) {
					prgDialog.dismiss();
				}

				if (result != null) {

					JSONObject obj = new JSONObject(result);

					// Log.d("json", result);

					if (obj.getBoolean("status")) {
						// mostrarProgress();
						Util.criarToast(getApplicationContext(),
								R.string.msgLoginSucesso);

						usuario = gson.fromJson(obj.getString("objeto"),
								Usuario.class);
						// Log.v("MyApp", usuario.getPerfil());
						if (!db.listaUsuario(usuario)) {
							db.inserir(usuario);
						}

						navigatetoHome();

					} else {
						Util.criarToast(getApplicationContext(),
								R.string.msgLoginErrado);
						// errorMsg.setText(obj.getString("error_msg"));
						// Toast.makeText(getApplicationContext(),
						// obj.getString("error_msg"), Toast.LENGTH_LONG)
						// .show();
					}
				} else {
					Util.criarToast(getApplicationContext(),
							R.string.msgDeErroWebservice);
				}

			} catch (JSONException e) {
				Util.criarToast(getApplicationContext(),
						R.string.msgDeErroWebservice);
			}
		}
	}

}
