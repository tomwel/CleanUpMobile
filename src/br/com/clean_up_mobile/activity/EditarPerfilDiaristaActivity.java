package br.com.clean_up_mobile.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.controller.CadastroController;
import br.com.clean_up_mobile.db.CidadeDB;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.DiaristaPerfilVOHttp;
import br.com.clean_up_mobile.task.EnderecoHttp;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Mask;
import br.com.clean_up_mobile.util.PlaceHolder;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.DiaristaPerfilVO;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

public class EditarPerfilDiaristaActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {
	ProgressDialog prgDialog;
	DiaristaVOTask mTaskDiarista;
	DiaristaPerfilVO diaristaVO = new DiaristaPerfilVO();
	EditText edtNome;
	EditText edtFone;
	EditText edtEmail;
	AutoCompleteTextView edtEndereco;
	EditText edtNovaSenha;
	Spinner spinnerCidade;
	Button btnConfirmar;
	Button btnCancelar;
	List<Cidade> cidades = new ArrayList<Cidade>();
	Cidade cidade;
	ArrayAdapter<String> adapter;
	ArrayList<Integer> especialidadesSelecionadas = new ArrayList<Integer>();
	Endereco mEndereco;
	EnderecoTask mTask;
	CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
	PlaceHolder placeHolder;
	UsuarioDB db;
	Usuario usuario = new Usuario();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_perfil_diarista);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		db = new UsuarioDB(getApplicationContext());
		usuario = db.listaUsuario();
		diaristaVO.setUsuario(usuario);
		
		iniciarDownload();
		
		spinnerCidade = (Spinner) findViewById(R.id.spinnerCidadeDiarista);
		spinnerCidade.setOnItemSelectedListener(this);

		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkBox1.setOnClickListener(checkBoxOnClickListener);

		checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
		checkBox2.setOnClickListener(checkBoxOnClickListener);

		checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
		checkBox3.setOnClickListener(checkBoxOnClickListener);

		checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
		checkBox4.setOnClickListener(checkBoxOnClickListener);

		checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
		checkBox5.setOnClickListener(checkBoxOnClickListener);
		

		edtNome = (EditText) findViewById(R.id.editTextEditarNomeDiarista);
		edtFone = (EditText) findViewById(R.id.editTextEditarTelefoneDiarista);
		edtFone.addTextChangedListener(Mask.insert("(##)#####-####", edtFone));
		edtEmail = (EditText) findViewById(R.id.editTextEditarEmailDiarista);
		edtNovaSenha = (EditText) findViewById(R.id.editTextEditarSenhaDiarista);
		edtEndereco = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewEditarEnderecoDiarista);

		edtEndereco.setOnClickListener(this);
		edtEndereco.setAdapter(new PlaceHolder(getApplicationContext(),
				R.layout.list_item_autocomplete));

		btnConfirmar = (Button) findViewById(R.id.buttonConfirmarEdicaoDiarista);
		btnConfirmar.setOnClickListener(btnConfirmarOnClickListener);
		btnCancelar = (Button) findViewById(R.id.buttonCancelarEdicaoDiarista);
		btnCancelar.setOnClickListener(btnCancelarOnClickListener);

		ArrayAdapter<Cidade> dataAdapter = new ArrayAdapter<Cidade>(this,
				android.R.layout.simple_spinner_item, cidades);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCidade.setAdapter(dataAdapter);

		atualizarEspecialidadesECidades();
	}

	private void loadSpinnerData() {
		CidadeDB db = new CidadeDB(getApplicationContext());
		cidades = db.listarCidades();
		ArrayAdapter<Cidade> dataAdapter = new ArrayAdapter<Cidade>(this,
				android.R.layout.simple_spinner_item, cidades);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCidade.setAdapter(dataAdapter);

	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item

		cidade = cidades.get(position);

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	private OnClickListener btnCancelarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent it = new Intent(getApplicationContext(), PerfilActivity.class);
			it.putExtra("usuario", usuario);
			startActivity(it);
			finish();

		}
	};

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

	private OnClickListener btnConfirmarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			validaForm();
		}
	};

	public void validaForm() {
		diaristaVO.setNome(edtNome.getText().toString());
		diaristaVO.setTelefone(edtFone.getText().toString());
		diaristaVO.getUsuario().setEmail((edtEmail.getText().toString()));
		if (!edtNovaSenha.getText().toString().trim()
				.equals(diaristaVO.getUsuario().getSenha())
				&& !edtNovaSenha.getText().toString().trim().equals("")) {
			diaristaVO.getUsuario().setSenha(edtNovaSenha.getText().toString());
		}
		diaristaVO.setCidade(cidade);
		diaristaVO.setNewEspecialidade(especialidadesSelecionadas);
		diaristaVO.getEndereco().setLogradouro(edtEndereco.getText().toString());

		if (((Util.isNotNull(diaristaVO.getNome())
				&& Util.isNotNull(diaristaVO.getTelefone())
				&& Util.isNotNull(diaristaVO.getEndereco().getLogradouro())
				&& Util.isNotNull(diaristaVO.getUsuario().getEmail()) && Util
					.isNotNull(diaristaVO.getUsuario().getSenha())))) {

			// Valida email
			if (!Util.validate(diaristaVO.getUsuario().getEmail())) {
				Util.criarToast(getApplicationContext(),
						R.string.msgEmailInvalido);
			} else {
				// Valida o tamanho da senha
				if (diaristaVO.getUsuario().getSenha().length() < 8) {
					Util.criarToast(getApplicationContext(),
							R.string.msgSenhaInvalido);
				} else {
					salvarEndereco();
				}
			}
		}
	}
	
	private void iniciarDownload() {

		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {
				mTaskDiarista = new DiaristaVOTask();
				mTaskDiarista.execute();
			}
	}
	
	class DiaristaVOTask extends AsyncTask<Void, Void, DiaristaPerfilVO> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected DiaristaPerfilVO doInBackground(Void... params) {
			try {
				return DiaristaPerfilVOHttp.retrieveDiarista(diaristaVO.getUsuario().getId());
			} catch (IOException i) {
				i.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(DiaristaPerfilVO result) {
			super.onPostExecute(result);
			if (result != null) {
				diaristaVO = result;
				diaristaVO.setFotoUsuario("null");
				spinnerCidade.setSelection(diaristaVO.getCidade().getCodigoCidade());
				edtNome.setText(diaristaVO.getNome());
				edtFone.setText(diaristaVO.getTelefone());
				edtEmail.setText(diaristaVO.getUsuario().getEmail());
				edtEndereco.setText(diaristaVO.getEndereco().getLogradouro());
				if  (diaristaVO.getEspecialidades().size() > 0){
					for (int i = 0; i < diaristaVO.getEspecialidades().size(); i++){
						Especialidade especialidade = new Especialidade();
						especialidade = diaristaVO.getEspecialidades().get(i);
						if(especialidade.getNomeEspecialidade().equals(checkBox1.getText().toString())){
							checkBox1.setChecked(true);
							especialidadesSelecionadas.add(especialidade.getCodigoEspecialidade());
						}else if (especialidade.getNomeEspecialidade().equals(checkBox2.getText().toString())){
							checkBox2.setChecked(true);
							especialidadesSelecionadas.add(especialidade.getCodigoEspecialidade());
						}else if (especialidade.getNomeEspecialidade().equals(checkBox3.getText().toString())){
							checkBox3.setChecked(true);
							especialidadesSelecionadas.add(especialidade.getCodigoEspecialidade());
						}else if (especialidade.getNomeEspecialidade().equals(checkBox4.getText().toString())){
							checkBox4.setChecked(true);
							especialidadesSelecionadas.add(especialidade.getCodigoEspecialidade());
						}else if (especialidade.getNomeEspecialidade().equals(checkBox5.getText().toString())){
							checkBox5.setChecked(true);
							especialidadesSelecionadas.add(especialidade.getCodigoEspecialidade());
						}
					}
				}
			} else {
				Util.criarToast(getApplicationContext(),
						R.string.msgDeErroWebservice);
			}
		}
	}

	private void atualizarEspecialidadesECidades() {
		if (Util.existeConexao(getApplicationContext())) {
			new AtualizarEspecilidadesECidadesTask().execute();
		} else {
			Util.criarToast(getApplicationContext(),
					R.string.msgDeErroWebservice);
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
			} else {
				loadSpinnerData();
			}
		}
	}

	private void mostrarProgressDialog(String texto) {
		prgDialog = new ProgressDialog(EditarPerfilDiaristaActivity.this);
		prgDialog.setMessage(texto);
		prgDialog.setTitle("Cadastro");
		prgDialog.setCancelable(true);
		prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		prgDialog.show();
	}

	public void alterarPerfil(DiaristaPerfilVO diaristaVO) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_ATUALIZARPERFILDIARISTA,
					diaristaVO).execute();
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Object diaristaVO;
		private String url;

		public HttpAsyncTask(String url, Object diaristaVO) {
			this.diaristaVO = diaristaVO;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, diaristaVO);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					Util.criarToast(getApplicationContext(),
							R.string.msgSucessoPerfil);
					Intent it = new Intent(getApplicationContext(), PerfilActivity.class);
					it.putExtra("usuario", usuario);
					startActivity(it);
					finish();
				} catch (Exception e) {
					Util.criarToast(getApplicationContext(),
							R.string.msgDeErroWebservice);
					e.printStackTrace();
				}
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
				return EnderecoHttp.autocomplete(edtEndereco.getText()
						.toString());
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
				diaristaVO.setEndereco(result.get(0));
				usuario = diaristaVO.getUsuario();
				alterarPerfil(diaristaVO);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
	@Override 
	public void onBackPressed() {
		Intent it = new Intent(getApplicationContext(), PerfilActivity.class);
		it.putExtra("usuario", usuario);
		startActivity(it);
		finish();

	     super.onBackPressed();
	 }
}
