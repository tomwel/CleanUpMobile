package br.com.clean_up_mobile.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.controller.CadastroController;
import br.com.clean_up_mobile.db.CidadeDB;
import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.task.EnderecoHttp;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Mask;
import br.com.clean_up_mobile.util.PlaceHolder;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.PessoaVO;

public class CadastroActivity extends Activity implements
		OnItemSelectedListener, OnClickListener {

	int tipoUsuario = 1;
	ArrayList<Integer> especialidadesSelecionadas = new ArrayList<Integer>();
	ProgressDialog prgDialog;
	EditText nameET;
	EditText lastNameET;
	EditText cpfET;
	EditText phoneET;
	AutoCompleteTextView addressET;
	EditText emailET;
	EditText pwdET;
	Spinner spinner;
	List<Cidade> cidades = new ArrayList<Cidade>();
	Cidade cidade;
	EnderecoTask mTask;
	ArrayAdapter<String> adapter;
	Endereco mEndereco;
	PessoaVO pessoaVO = new PessoaVO();
	PlaceHolder placeHolder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		spinner = (Spinner) findViewById(R.id.spinnerCidade);
		spinner.setOnItemSelectedListener(this);

		nameET = (EditText) findViewById(R.id.registerName1);
		lastNameET = (EditText) findViewById(R.id.registerName2);
		cpfET = (EditText) findViewById(R.id.registerCPF);
		cpfET.addTextChangedListener(Mask.insert("###.###.###-##", cpfET));
		phoneET = (EditText) findViewById(R.id.registerPhone);
		phoneET.addTextChangedListener(Mask.insert("(##)#####-####", phoneET));
		addressET = (AutoCompleteTextView) findViewById(R.id.registerAddress);
		emailET = (EditText) findViewById(R.id.registerEmail);
		pwdET = (EditText) findViewById(R.id.registerPassword);
		
		
		addressET.setOnClickListener(this);
		addressET.setAdapter(new PlaceHolder(getApplicationContext(),
				R.layout.list_item_autocomplete));
		
		Button btnRegister = (Button) findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(btnRegisterOnClickListener);

		Button btnLogin = (Button) findViewById(R.id.btnCancelarCadastro);
		btnLogin.setOnClickListener(btnLoginOnClickListener);

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

		ArrayAdapter<Cidade> dataAdapter = new ArrayAdapter<Cidade>(this,
				android.R.layout.simple_spinner_item, cidades);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		atualizarEspecialidadesECidades();

	}

	private void loadSpinnerData() {
		CidadeDB db = new CidadeDB(getApplicationContext());
		cidades = db.listarCidades();
		ArrayAdapter<Cidade> dataAdapter = new ArrayAdapter<Cidade>(this,
				android.R.layout.simple_spinner_item, cidades);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item

		cidade = cidades.get(position);

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	OnClickListener checkBoxOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			boolean checked = ((CompoundButton) view).isChecked();

			int index = 0;
			switch (view.getId()) {
			case R.id.checkBox1:
				if (checked) {
					especialidadesSelecionadas.add(1);

				} else {
					index = especialidadesSelecionadas.indexOf(1);
					especialidadesSelecionadas.remove(index);

				}
				break;
			case R.id.checkBox2:
				if (checked) {
					especialidadesSelecionadas.add(2);

				} else {
					index = especialidadesSelecionadas.indexOf(2);
					especialidadesSelecionadas.remove(index);

				}
				break;
			case R.id.checkBox3:
				if (checked) {
					especialidadesSelecionadas.add(3);

				} else {
					index = especialidadesSelecionadas.indexOf(3);
					especialidadesSelecionadas.remove(index);

				}
				break;
			case R.id.checkBox4:
				if (checked) {
					especialidadesSelecionadas.add(4);

				} else {
					index = especialidadesSelecionadas.indexOf(4);
					especialidadesSelecionadas.remove(index);

				}
				break;
			case R.id.checkBox5:
				if (checked) {
					especialidadesSelecionadas.add(5);

				} else {
					index = especialidadesSelecionadas.indexOf(5);
					especialidadesSelecionadas.remove(index);

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

	private OnClickListener btnLoginOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			navigatetoLoginActivity();
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

		if (((Util.isNotNull(name) && Util.isNotNull(lastname)
				&& Util.isNotNull(cpf) && Util.isNotNull(phone)
				&& Util.isNotNull(address) && Util.isNotNull(email) && Util
					.isNotNull(password)))
				||

				(tipoUsuario == 1 && (Util.isNotNull(name)
						&& Util.isNotNull(lastname) && Util.isNotNull(cpf)
						&& Util.isNotNull(phone) && Util.isNotNull(email) && Util
							.isNotNull(password)))) {

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

						pessoaVO.setNome(name + " " + lastname);
						pessoaVO.setCpf(cpf);
						pessoaVO.setTelefone(phone);

						pessoaVO.setCidade(cidade.getCodigoCidade());
						pessoaVO.setEmail(email);
						pessoaVO.setSenha(password);
						pessoaVO.setTipo(tipoUsuario);
						pessoaVO.setEspecialidades(especialidadesSelecionadas);

						if(tipoUsuario == 1){
							doCadastroUsuario(pessoaVO);
						}else if(tipoUsuario == 0){
							salvarEndereco();
						}
					}
				}
			}
		} else {
			Util.criarToast(getApplicationContext(),
					R.string.msgFormularioVazio);
		}
	}

	private void doCadastroUsuario(PessoaVO pessoa) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_CADASTRO, pessoa).execute();
	}

	private void atualizarEspecialidadesECidades() {
		if (Util.existeConexao(getApplicationContext())) {
			new AtualizarEspecilidadesECidadesTask().execute();
		} else {
			navigatetoLoginActivity();
		}

	}

	private void navigatetoLoginActivity() {
		finish();
	}

	private void setDefaultValues() {
		nameET.setText("");
		lastNameET.setText("");
		cpfET.setText("");
		phoneET.setText("");
		addressET.setText("");
		emailET.setText("");
		pwdET.setText("");

	}

	private void mostrarProgressDialog(String texto) {
		prgDialog = new ProgressDialog(CadastroActivity.this);
		prgDialog.setMessage(texto);
		prgDialog.setTitle("Cadastro");
		prgDialog.setCancelable(true);
		prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		prgDialog.show();
	}

	class HttpAsyncTask extends AsyncTask<Void, Void, Boolean> {

		private Object o;
		private String url;

		public HttpAsyncTask(String url, Object o) {
			this.o = o;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
			mostrarProgressDialog("Realizando cadastro");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			CadastroController cc = new CadastroController(
					getApplicationContext());
			return cc.cadastrar(url, o);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (prgDialog.isShowing()) {
				prgDialog.dismiss();
			}
			if (result) {
				Util.criarToast(getApplicationContext(),
						R.string.msgCadastroRealizado);
				setDefaultValues();
				navigatetoLoginActivity();
			}
		}
	}

	class AtualizarEspecilidadesECidadesTask extends
			AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			mostrarProgressDialog("Atualizando informações.");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			CadastroController cc = new CadastroController(
					getApplicationContext());
			return cc.atualizarEspecialidadesECidades();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (prgDialog.isShowing()) {
				prgDialog.dismiss();
			}
			if (!result) {
				Util.criarToast(getApplicationContext(),
						R.string.msgDeErroWebservice);
				navigatetoLoginActivity();
			} else {
				loadSpinnerData();
			}
		}
	}
	
	private void salvarEndereco() {

		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {

			mTask = new EnderecoTask();
			mTask.execute();
		}
	}

	class EnderecoTask extends AsyncTask<Void, Void, ArrayList<Endereco>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected ArrayList<Endereco> doInBackground(Void... params) {
			try {
				return EnderecoHttp.autocomplete(addressET.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(ArrayList<Endereco> result) {
			super.onPostExecute(result);
			if (result != null) {
				mEndereco = result.get(0);
				pessoaVO.setEndereco(result.get(0).getLogradouro());
				pessoaVO.setLat(result.get(0).getLat());
				pessoaVO.setLng(result.get(0).getLng());
				doCadastroUsuario(pessoaVO);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
