package br.com.clean_up_mobile.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.util.WebService;

public class LoginActivity extends Activity {

	ProgressDialog prgDialog;
	TextView errorMsg;
	EditText emailET;
	EditText pwdET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		errorMsg = (TextView) findViewById(R.id.login_error);
		emailET = (EditText) findViewById(R.id.loginEmail);
		pwdET = (EditText) findViewById(R.id.loginPassword);
		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(btnLoginOnClickListener);

	}

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
				Toast.makeText(getApplicationContext(),
						"Please enter valid email", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Please fill the form, don't leave any field blank",
					Toast.LENGTH_LONG).show();
		}
	}

	public void doLogin(Usuario usuario) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_LOGIN, usuario).execute();
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Usuario u;
		private String url;

		public HttpAsyncTask(String url, Usuario u) {
			this.u = u;
			this.url = url;
		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, u);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			try {

				if (result != null) {

					JSONObject obj = new JSONObject(result);

					if (obj.getBoolean("status")) {
						Toast.makeText(getApplicationContext(),
								"You are successfully logged in!",
								Toast.LENGTH_LONG).show();
						// Navigate to Home screen
						navigatetoHomeActivity();
					} else {
						errorMsg.setText(obj.getString("error_msg"));
						Toast.makeText(getApplicationContext(),
								obj.getString("error_msg"), Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Erro na conex�o tente novamente!",
							Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Toast.makeText(
						getApplicationContext(),
						"Error Occured [Server's JSON response might be invalid]!",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();

			}
		}

	}

	/**
	 * Method which navigates from Login Activity to Home Activity
	 */
	public void navigatetoHomeActivity() {
		Intent homeIntent = new Intent(getApplicationContext(),
				HomeActivity.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeIntent);
	}

	/**
	 * Method gets triggered when Register button is clicked
	 * 
	 * @param view
	 */
	public void navigatetoRegisterActivity(View view) {
		Intent loginIntent = new Intent(getApplicationContext(),
				CadastroActivity.class);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginIntent);
	}
}
