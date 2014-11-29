package br.com.clean_up_mobile.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.activity.CalendarActivity;
import br.com.clean_up_mobile.activity.HomeActivity;
import br.com.clean_up_mobile.db.DiaristaFavoritaDB;
import br.com.clean_up_mobile.db.EspecialidadeDB;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.EnderecoHttp;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.PlaceHolder;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.DiaristaVO;
import br.com.clean_up_mobile.vo.ServicoVO;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetalheDiaristaFragment extends Fragment implements
		OnClickListener {

	Diarista diarista;
	Usuario usuario;
	ServicoVO servicoVo;
	UsuarioDB db;
	DiaristaFavoritaDB dbDiaristaFav;
	EspecialidadeDB dbEspecialidade;
	PlaceHolder placeHolder;
	TextView txtNome;
	TextView txtCidade;
	TextView txtEspecialidades;
	ImageView imgDiarista;
	EditText data;
	EditText descricao;
	AutoCompleteTextView enderecoCompleto;
	AutoCompleteTextView endereco;
	ArrayAdapter<String> adapter;
	ArrayList<Endereco> mEnderecos;
	EnderecoTask mTask;

	public static DetalheDiaristaFragment novaInstancia(Diarista diarista) {
		Bundle args = new Bundle();
		args.putSerializable("diarista", diarista);
		DetalheDiaristaFragment f = new DetalheDiaristaFragment();
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		db = new UsuarioDB(getActivity());
		dbDiaristaFav = new DiaristaFavoritaDB(getActivity());
		dbEspecialidade = new EspecialidadeDB(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.item_detalhe_diarista, null);

		txtNome = (TextView) layout
				.findViewById(R.id.textViewNomeDiaristaDetalhe);
		txtCidade = (TextView) layout
				.findViewById(R.id.textViewCidadeDiaristaDetalhe);
		txtEspecialidades = (TextView) layout
				.findViewById(R.id.textViewEspecialidadesDiaristaDetalhe);
		imgDiarista = (ImageView) layout
				.findViewById(R.id.imageViewDiaristaDetalhe);

		data = (EditText) layout.findViewById(R.id.editTextDataServico);
		descricao = (EditText) layout
				.findViewById(R.id.editTextDescricaoServico);
		endereco = (AutoCompleteTextView) layout
				.findViewById(R.id.autoCompleteTextEnderecoServico);
		Button btConfirmar = (Button) layout
				.findViewById(R.id.buttonConfirmarDiarista);
		btConfirmar.setOnClickListener(btnConfirmarOnClickListener);
		Button btCancelar = (Button) layout
				.findViewById(R.id.buttonCancelarDiarista);
		btCancelar.setOnClickListener(btnCancelarOnClickListener);
		Button btData = (Button) layout.findViewById(R.id.buttonCalendario);
		btData.setOnClickListener(btnCalendarioOnClickListener);

		endereco.setOnClickListener(this);
		endereco.setAdapter(new PlaceHolder(getActivity(),
				R.layout.list_item_autocomplete));

		diarista = (Diarista) getArguments().getSerializable("diarista");
		// diarista.favorito = dbDiaristaFav.favorito(diarista);

		List<Especialidade> especialidades = diarista.getEspecialidades();
		Especialidade especialidade = new Especialidade();
		String listaEspecialidades = "";
		for (int i = 0; i < especialidades.size(); i++) {
			if (especialidades.get(i) != null) {
				especialidade = especialidades.get(i);
				boolean existeEspecialidade;
				existeEspecialidade = dbEspecialidade
						.existeEspecialidade(especialidade
								.getCodigoEspecialidade());

				if (!existeEspecialidade) {
					dbEspecialidade.inserirEspecialidade(especialidade);
					dbEspecialidade.inserirRelacionamentoEspecialidadeDiarista(
							diarista.getCodigo(),
							especialidade.getCodigoEspecialidade());
				}

				listaEspecialidades = listaEspecialidades + "* "
						+ especialidade.getNomeEspecialidade() + "\n";
			} else {
				break;
			}
		}
		txtNome.setText(diarista.getNome());
		txtCidade.setText(diarista.getCidade());
		txtEspecialidades.setText(listaEspecialidades);

		return layout;
	}

	public void validaForm() {

		if (data.getText().toString().trim() == ""
				&& endereco.getText().toString().trim() == "") {
			Util.criarToast(getActivity(), R.string.msgServicoDataEndereco);
		} else if (data.getText().toString().trim() == "") {
			Util.criarToast(getActivity(), R.string.msgServicoData);
		} else if (endereco.getText().toString().trim() == "") {
			Util.criarToast(getActivity(), R.string.msgServicoEndereco);
		} else {
			salvarEndereco();
		}
	}

	public void gravarServico() throws ParseException {

		DiaristaVO diaristaVO = new DiaristaVO();
		diaristaVO.setCodigo(diarista.getCodigo());

		servicoVo = new ServicoVO();
		usuario = db.listaUsuario();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Date date = fmt.parse(data.getText().toString());
		cal.setTime(date);
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH) + 1;
		int ano = cal.get(Calendar.YEAR);
		String strDate = ano + "-" + mes + "-" + dia;
		servicoVo.setData(strDate);
		servicoVo.setDescricao(descricao.getText().toString());
		servicoVo.setDiarista(diaristaVO);
		servicoVo.setUsuario(usuario);
		servicoVo.setEnderecos(mEnderecos);
		contratarServico(servicoVo);
	}

	private OnClickListener btnConfirmarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			validaForm();
		}
	};

	private OnClickListener btnCancelarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuario = db.listaUsuario();
			Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
			homeIntent.putExtra("usuario", usuario);
			homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeIntent);
		}
	};

	private OnClickListener btnCalendarioOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent calendarIntent = new Intent(getActivity(),
					CalendarActivity.class);
			startActivityForResult(calendarIntent, 1);
		}
	};

	public void contratarServico(ServicoVO servico) {
		if (Util.existeConexao(getActivity()))
			new HttpAsyncTask(Constantes.CONTRATAR_SERVICO, servico).execute();
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Object serv;
		private String url;

		public HttpAsyncTask(String url, Object serv) {
			this.serv = serv;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, serv);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

				try {
					Util.criarToast(getActivity(),
							R.string.msgServicoSolicitado);
					Intent homeIntent = new Intent(getActivity(),
							HomeActivity.class);
					homeIntent.putExtra("usuario", usuario);
					homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(homeIntent);
				} catch (Exception e) {
					Util.criarToast(getActivity(), R.string.msgDeErroWebservice);
					e.printStackTrace();
				}
			}
		}
	}

	private void salvarEndereco() {

		ConnectivityManager cm = (ConnectivityManager) getActivity()
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
				return EnderecoHttp.autocomplete(endereco.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(ArrayList<Endereco> result) {
			super.onPostExecute(result);
			if (result != null) {
				mEnderecos = result;
				try {
					gravarServico();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
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
	public void onActivityResult(int requestCode, int resultCode, Intent date) {
		switch (requestCode) {
		case 1:
			if (date != null) {
				if (date.getExtras().getString("data_servico") != null) {
					data.setText(date.getExtras().getString("data_servico"));
				}

				super.onActivityResult(requestCode, resultCode, date);
			}
			break;
		}

	}
}
