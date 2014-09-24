package br.com.clean_up_mobile.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.PessoaVO;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Mask;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.util.WebService;

public class CadastroActivity extends Activity {

	int tipoUsuario = 0;
	int[] arrEspecialidades;

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
		phoneET.addTextChangedListener(Mask.insert("(##)#####-####", phoneET));
		addressET = (EditText) findViewById(R.id.registerAddress);
		emailET = (EditText) findViewById(R.id.registerEmail);
		pwdET = (EditText) findViewById(R.id.registerPassword);

		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(btnRegisterOnClickListener);

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tipo_usuario);
		radioGroup.setOnCheckedChangeListener(btnRadioOnCheckedChangeListener);
	}

	// public void onCheckboxClicked(View view) {
	// boolean checked = ((CheckBox) view).isChecked();
	//
	// switch(view.getId()) {
	// case R.id.checkBox1:
	// if (checked){
	// arrEspecialidades[1] = 1;
	// } else {
	// arrEspecialidades[1] = null;
	// }
	// break;
	// case R.id.checkBox2:
	// if (checked){
	// arrEspecialidades[2] = 2;
	// }else{
	// arrEspecialidades[2] = null;
	// }
	// break;
	// case R.id.checkBox3:
	// if (checked){
	// arrEspecialidades[3] = 3;
	// } else {
	// arrEspecialidades[3] = null;
	// }
	// break;
	// case R.id.checkBox4:
	// if (checked){
	// arrEspecialidades[4] = 4;
	// }else{
	// arrEspecialidades[4] = null;
	// }
	// break;
	// case R.id.checkBox5:
	// if (checked){
	// arrEspecialidades[5] = 5;
	// }else{
	// arrEspecialidades[5] = null;
	// }
	// break;
	// }
	// }

	OnCheckedChangeListener btnRadioOnCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			LinearLayout ll = (LinearLayout) findViewById(R.id.specialty);

			switch (checkedId) {
			case R.id.radio_cliente:
				ll.setVisibility(View.GONE);
				tipoUsuario = 1;
				break;
			case R.id.radio_diarista:
				ll.setVisibility(View.VISIBLE);
				tipoUsuario = 0;
				break;
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

		if (Util.isNotNull(name) && Util.isNotNull(lastname)
				&& Util.isNotNull(cpf) && Util.isNotNull(phone)
				&& Util.isNotNull(address) && Util.isNotNull(email)
				&& Util.isNotNull(password)) {

			if (Util.check(cpf)) {

				String cpfLimpo = Util.limpaCpf(cpf);
				String telefoneLimpo = Util.limpaTelefone(phone);

				PessoaVO p = new PessoaVO();
				p.setNome(name + " " + lastname);
				p.setCpf(cpfLimpo);
				p.setTelefone(telefoneLimpo);
				p.setEndereco(address);
				p.setCidade(1);
				p.setEmail(email);
				p.setSenha(password);
				p.setTipo(tipoUsuario);
				p.setEspecialidades(arrEspecialidades);

				doCadastroUsuario(p);

			} else {
				Toast.makeText(getApplicationContext(),
						"Please enter valid CPF", Toast.LENGTH_LONG).show();
			}

			if (!Util.validate(email)) {
				Toast.makeText(getApplicationContext(),
						"Please enter valid E-MAIL", Toast.LENGTH_LONG).show();
			}

			if (pwdET.length() < 8) { // verifica tamanho do conteúdo do
				Toast.makeText(getApplicationContext(),
						"Enter Password (Min 8, Max 14)", Toast.LENGTH_LONG)
						.show();
			}

		} else {
			Toast.makeText(getApplicationContext(),
					"Please fill the form, don't leave any field blank",
					Toast.LENGTH_LONG).show();
		}

	}

	public void doCadastroUsuario(PessoaVO pessoa) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_CADASTRO, pessoa).execute();
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

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Object o;
		private String url;

		public HttpAsyncTask(String url, Object o) {
			this.o = o;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
			prgDialog = new ProgressDialog(getApplicationContext());
			prgDialog.setMessage("Sincronizando informações.");
			prgDialog.setTitle("Sincronizando");
			prgDialog.setCancelable(true);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			prgDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			Log.v("CLUP", "dfdd" + url);
			return WebService.getREST(url, o);
		}

		@Override
		protected void onPostExecute(String result) {

			try {
				if (prgDialog.isShowing()) {
					prgDialog.dismiss();
				}
				if (result != null) {

					JSONObject obj = new JSONObject(result);

					if (obj.getBoolean("status")) {
						setDefaultValues();
						Util.criarToast(getApplicationContext(),
								"Cadastrado com sucesso");
					} else {
						String mensagemErrro;
						if (obj.getString("error_msg").equals("cpf_cadastrado")) {
							mensagemErrro = "CPF já cadastrado";
						} else if (obj.getString("error_msg").equals(
								"email_cadastrado")) {
							mensagemErrro = "Email já cadastrado";
						} else {
							mensagemErrro = obj.getString("error_msg");
						}
						Util.criarToast(getApplicationContext(), mensagemErrro);
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
}
