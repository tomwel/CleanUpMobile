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
import android.widget.TextView;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.util.WebService;

public class LoginActivity extends Activity {

	ProgressDialog prgDialog;
	TextView errorMsg;
	EditText emailET;
	EditText pwdET;

	public static final String LOGIN_USU = "http://192.168.42.93:8080/cleanUp/login/mobile";

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
				
				String json = Util.convertJSON(u);

				new HttpAsyncTask().execute(LOGIN_USU, json);
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

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... dados) {

			return WebService.getREST(dados[0], dados[1]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			try {
				System.out.println(result);
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
