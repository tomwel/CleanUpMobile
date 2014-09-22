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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Pessoa;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Mask;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.util.WebService;

/**
 * 
 * Register Activity Class
 */
public class CadastroActivity extends Activity {

	ProgressDialog prgDialog;
	TextView errorMsg;
	EditText nameET;
	EditText lastNameET;
	EditText cpfET;
	EditText phoneET;
	EditText addressET;
	EditText emailET;
	EditText pwdET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);

		errorMsg = (TextView) findViewById(R.id.register_error);
		
		nameET = (EditText) findViewById(R.id.registerName1);
		
		lastNameET = (EditText) findViewById(R.id.registerName2);
		
		cpfET = (EditText) findViewById(R.id.registerCPF);
		cpfET.addTextChangedListener(Mask.insert("###.###.###-##", cpfET));
		
		phoneET = (EditText) findViewById(R.id.registerPhone);
		phoneET.addTextChangedListener(Mask.insert("(##)####-####", phoneET));
		
		addressET =(EditText)findViewById(R.id.registerAddress);
		
		emailET = (EditText) findViewById(R.id.registerEmail);
		
		
		pwdET = (EditText) findViewById(R.id.registerPassword);
		
		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);

		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(btnRegisterOnClickListener);
		
		CheckBox checkDiarista = (CheckBox)findViewById(R.id.checkBox6);
		checkDiarista.setOnClickListener(chckSelDiarista);
		
		
		
		
	}
	
	private OnClickListener chckSelDiarista = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			LinearLayout ll = (LinearLayout)findViewById(R.id.specialty);
			
				if (((CheckBox) v).isChecked()) {
					
					ll.setVisibility(View.VISIBLE);
				}else{
					
					ll.setVisibility(View.GONE);
				}
	
			
		}
	};
	
	
			
	
	

	private OnClickListener btnRegisterOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			validaForm();

			
		}
	};

	
	
	
	public void validaForm() {

		String name = nameET.getText().toString();
		String lastname = lastNameET.getText().toString();
		String cpf = cpfET.getText().toString();
		String phone = phoneET.getText().toString();
		String address = addressET.getText().toString();
		String email = emailET.getText().toString();
		String password = pwdET.getText().toString();
		

		
		if (Util.isNotNull(name)
				&& Util.isNotNull(lastname)
				&& Util.isNotNull(cpf)
				&& Util.isNotNull(phone)
				&& Util.isNotNull(address)
				&& Util.isNotNull(email)
				&& Util.isNotNull(password)) {
			
			if  (Util.check(cpf)) 
			{

				// monta objeto usuario
				// chama a função cadastrar usuario

			} else 
				{	
				Toast.makeText(getApplicationContext(),
						"Please enter valid CPF", Toast.LENGTH_LONG).show();
				}


			if  (Util.validate(email)) 
			{

				// monta objeto usuario
				// chama a função cadastrar usuario

			} else 
				{	
				Toast.makeText(getApplicationContext(),
						"Please enter valid E-MAIL", Toast.LENGTH_LONG).show();
				}
			
			
			if(pwdET.length() >= 8 ) { //verifica tamanho do conteúdo do EditText
				// monta objeto usuario
				// chama a função cadastrar usuario

	        }
	        else {
	        	Toast.makeText(getApplicationContext(),
						"Enter Password (Min 8, Max 14)", Toast.LENGTH_LONG).show();
	        }
			
			
			
			
			

		} else {
			Toast.makeText(getApplicationContext(),
					"Please fill the form, don't leave any field blank",
					Toast.LENGTH_LONG).show();
		}

	}

	public void doCadastroUsuario(Pessoa pessoa) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_CADASTRO, pessoa).execute();
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Object o;
		private String url;

		public HttpAsyncTask(String url, Object o) {
			this.o = o;
			this.url = url;
		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, o);
		}

		@Override
		protected void onPostExecute(String result) {
			try {

				if (result != null) {

					JSONObject obj = new JSONObject(result);

					if (obj.getBoolean("status")) {
						setDefaultValues();
						Toast.makeText(getApplicationContext(),
								"You are successfully registered!",
								Toast.LENGTH_LONG).show();
					} else {
						errorMsg.setText(obj.getString("error_msg"));
						Toast.makeText(getApplicationContext(),
								obj.getString("error_msg"), Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Erro na conexão tente novamente!",
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

	public void navigatetoLoginActivity(View view) {
		Intent loginIntent = new Intent(getApplicationContext(),
				LoginActivity.class);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginIntent);
	}

	public void setDefaultValues() {
		nameET.setText("");
		lastNameET.setText("");
		cpfET.setText("");
		phoneET.setText("");
		addressET.setText("");
		emailET.setText("");
		pwdET.setText("");
		
		
		
	}

}
