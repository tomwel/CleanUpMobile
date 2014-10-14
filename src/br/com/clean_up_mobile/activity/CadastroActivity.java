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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Mask;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.PessoaVO;

public class CadastroActivity extends Activity {

	int tipoUsuario = 1;
	int[] arrEspecialidades = new int[5];

	ProgressDialog prgDialog;
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

		// CheckBox

		CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkBox1.setOnClickListener(checkBoxOnClickListener);

		CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
		checkBox2.setOnClickListener(checkBoxOnClickListener);

		CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
		checkBox3.setOnClickListener(checkBoxOnClickListener);

		CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
		checkBox4.setOnClickListener(checkBoxOnClickListener);

		CheckBox checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
		checkBox5.setOnClickListener(checkBoxOnClickListener);

		// Spinner

		// Spinner spiCidade = (Spinner) findViewById(R.id.spinner1);
		// spiCidade.setOnItemSelectedListener(spiCidadeOnItemSelectedListener);

	}

	// OnItemSelectedListener spiCidadeOnItemSelectedListener = new
	// OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> parent, View view,
	// int pos, long id) {
	//
	// Util.criarToast(getApplicationContext(), pos + " - " + id);
	//
	// }
	//
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// };

	OnClickListener checkBoxOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			boolean checked = ((CompoundButton) view).isChecked();

			Toast.makeText(getApplicationContext(),
					"info " + view.getId() + "  " + checked, Toast.LENGTH_LONG)
					.show();

			switch (view.getId()) {
			case R.id.checkBox1:
				if (checked) {
					arrEspecialidades[0] = 1;
				} else {
					arrEspecialidades[0] = 0;
				}
				break;
			case R.id.checkBox2:
				if (checked) {
					arrEspecialidades[1] = 2;
				} else {
					arrEspecialidades[1] = 0;
				}
				break;
			case R.id.checkBox3:
				if (checked) {
					arrEspecialidades[2] = 3;
				} else {
					arrEspecialidades[2] = 0;
				}
				break;
			case R.id.checkBox4:
				if (checked) {
					arrEspecialidades[3] = 4;
				} else {
					arrEspecialidades[3] = 0;
				}
				break;
			case R.id.checkBox5:
				if (checked) {
					arrEspecialidades[4] = 5;
				} else {
					arrEspecialidades[4] = 0;
				}
				break;
			}
		}
	};

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

			// Valida email
			if (!Util.validate(email)) {
				Util.criarToast(getApplicationContext(),
						R.string.msgEmailInvalido);
			} else {
				// Valida o tamanho da senha
				if (pwdET.length() < 8) {
					Util.criarToast(getApplicationContext(),
							R.string.msgSenhaInvalido);
				} else {
					// Valida o cpf
					if (!Util.check(cpf)) {
						Util.criarToast(getApplicationContext(),
								R.string.msgCpfInvalido);
					} else {

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
					}
				}
			}
		} else {
			Util.criarToast(getApplicationContext(),
					R.string.msgFormularioVazio);
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
			prgDialog = new ProgressDialog(CadastroActivity.this);
			prgDialog.setMessage("Ralizando Cadastro");
			prgDialog.setTitle("Cadastro");
			prgDialog.setCancelable(true);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			prgDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
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

					// cadastro realizado
					if (obj.getBoolean("status")) {
						setDefaultValues();
						Util.criarToast(getApplicationContext(),
								R.string.msgCadastroRealizado);
					} else {

						int mensagemErrro;

						// cpf já cadastrado
						if (obj.getString("error_msg").equals("cpf_cadastrado")) {
							mensagemErrro = R.string.msgCpfCadastrado;
							// email já cadastrado
						} else if (obj.getString("error_msg").equals(
								"email_cadastrado")) {
							mensagemErrro = R.string.msgEmailCadastrado;
							// erro no servidor ao cadastrar
						} else {
							mensagemErrro = R.string.msgDeErroWebservice;
						}
						Util.criarToast(getApplicationContext(), mensagemErrro);
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
