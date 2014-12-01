package br.com.clean_up_mobile.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.controller.CadastroController;
import br.com.clean_up_mobile.db.CidadeDB;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.ClienteVOHttp;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Mask;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.ClienteVO;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditarPerfilClienteActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {
	ProgressDialog prgDialog;
	ClienteVO clienteVO = new ClienteVO();
	EditText edtNome;
	EditText edtFone;
	EditText edtEmail;
	EditText edtNovaSenha;
	Button btnConfirmar;
	Button btnCancelar;
	List<Cidade> cidades = new ArrayList<Cidade>();
	Cidade cidade;
	Spinner spinnerCidade;
	ArrayAdapter<String> adapter;
	UsuarioDB db;
	Usuario usuario = new Usuario();
	ClienteVOTask mTaskCliente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_perfil_cliente);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		db = new UsuarioDB(getApplicationContext());
		usuario = db.listaUsuario();
		clienteVO.setCodUsuario(usuario.getId());

		iniciarDownload();

		spinnerCidade = (Spinner) findViewById(R.id.spinnerEdicaoCidadeCliente);
		spinnerCidade.setOnItemSelectedListener(this);
		edtNome = (EditText) findViewById(R.id.editTextEditarNome);
		edtFone = (EditText) findViewById(R.id.editTextEditarTelefone);
		edtFone.addTextChangedListener(Mask.insert("(##)#####-####", edtFone));
		edtEmail = (EditText) findViewById(R.id.editTextEditarEmail);
		edtNovaSenha = (EditText) findViewById(R.id.editTextEditarSenha);
		btnConfirmar = (Button) findViewById(R.id.buttonConcluirEdicaoPerfil);
		btnConfirmar.setOnClickListener(btnConfirmarOnClickListener);
		btnCancelar = (Button) findViewById(R.id.buttonCancelarEdicaoPerfil);
		btnCancelar.setOnClickListener(btnCancelarOnClickListener);

		ArrayAdapter<Cidade> dataAdapter = new ArrayAdapter<Cidade>(this,
				android.R.layout.simple_spinner_item, cidades);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCidade.setAdapter(dataAdapter);

		AtualizarCidades();
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

	private void iniciarDownload() {

		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {
			mTaskCliente = new ClienteVOTask();
			mTaskCliente.execute();
		}
	}

	class ClienteVOTask extends AsyncTask<Void, Void, ClienteVO> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected ClienteVO doInBackground(Void... params) {
			try {
				return ClienteVOHttp.retrieveCliente(usuario.getId());
			} catch (IOException i) {
				i.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ClienteVO result) {
			super.onPostExecute(result);
			if (result != null) {
				clienteVO = result;
				clienteVO.setFotoUsuario("null");
				edtNome.setText(clienteVO.getNome());
				edtFone.setText(clienteVO.getTelefone());
				edtEmail.setText(clienteVO.getEmail());
				spinnerCidade.setSelection(clienteVO.getCidade()
						.getCodigoCidade());
			} else {
				Util.criarToast(getApplicationContext(),
						R.string.msgDeErroWebservice);
			}
		}
	}

	private OnClickListener btnCancelarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent it = new Intent(getApplicationContext(),
					PerfilActivity.class);
			it.putExtra("usuario", usuario);
			startActivity(it);
			finish();

		}
	};

	private OnClickListener btnConfirmarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			validaForm();
		}
	};

	public void validaForm() {
		clienteVO.setNome(edtNome.getText().toString());
		clienteVO.setTelefone(edtFone.getText().toString());
		clienteVO.setEmail(edtEmail.getText().toString());
		clienteVO.setCidade(cidade);
		if (!edtNovaSenha.getText().toString().trim()
				.equals(clienteVO.getSenha())
				&& !edtNovaSenha.getText().toString().trim().equals("")) {
			clienteVO.setSenha(edtNovaSenha.getText().toString());
		}
		if (((Util.isNotNull(clienteVO.getNome())
				&& Util.isNotNull(clienteVO.getTelefone())
				&& Util.isNotNull(clienteVO.getEmail()) && Util
					.isNotNull(clienteVO.getSenha())))) {

			// Valida email
			if (!Util.validate(clienteVO.getEmail())) {
				Util.criarToast(getApplicationContext(),
						R.string.msgEmailInvalido);
			} else {
				// Valida o tamanho da senha
				if (clienteVO.getSenha().length() < 8) {
					Util.criarToast(getApplicationContext(),
							R.string.msgSenhaInvalido);
				} else {
					
					clienteVO.setCodUsuario(usuario.getId());
					alterarPerfil(clienteVO);
				}
			}
		}
	}

	private void AtualizarCidades() {
		if (Util.existeConexao(getApplicationContext())) {
			new AtualizarCidadesTask().execute();
		} else {
			Util.criarToast(getApplicationContext(),
					R.string.msgDeErroWebservice);
		}

	}

	class AtualizarCidadesTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			mostrarProgressDialog("Atualizando informações.");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			CadastroController cc = new CadastroController(
					getApplicationContext());
			return cc.atualizaCidades();
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
		prgDialog = new ProgressDialog(EditarPerfilClienteActivity.this);
		prgDialog.setMessage(texto);
		prgDialog.setTitle("Cadastro");
		prgDialog.setCancelable(true);
		prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		prgDialog.show();
	}

	public void alterarPerfil(ClienteVO clienteVO) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_ATUALIZARPERFILCLIENTE, clienteVO)
					.execute();
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Object clienteVO;
		private String url;

		public HttpAsyncTask(String url, Object clienteVo) {
			this.clienteVO = clienteVo;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, clienteVO);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

				try {
					Util.criarToast(getApplicationContext(),
							R.string.msgSucessoPerfil);
					Intent it = new Intent(getApplicationContext(),
							PerfilActivity.class);
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
