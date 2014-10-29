package br.com.clean_up_mobile.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.ServicoVO;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalheDiaristaFragment extends Fragment{

	Diarista diarista;
	Usuario usuario;
	ServicoVO servicoVo;
	UsuarioDB db;
	
	TextView txtNome;
	TextView txtCidade;
	TextView txtEspecialidades;
	ImageView imgDiarista;
	EditText data;
	EditText descricao;
	EditText endereco;
	Button btConfirmar, btCancelar;

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

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.item_detalhe_diarista, null);
		
		txtNome = (TextView) layout.findViewById(R.id.textViewNomeDiaristaDetalhe);
		txtCidade = (TextView) layout.findViewById(R.id.textViewCidadeDiaristaDetalhe);
		txtEspecialidades = (TextView) layout.findViewById(R.id.textViewEspecialidadesDiaristaDetalhe);
		imgDiarista = (ImageView) layout.findViewById(R.id.imageViewDiaristaDetalhe);
		data = (EditText) layout.findViewById(R.id.editTextDataServico);
		descricao = (EditText) layout.findViewById(R.id.editTextDescricaoServico);
		endereco = (EditText) layout.findViewById(R.id.editTextEnderecoServico);
		btConfirmar = (Button) layout.findViewById(R.id.buttonConfirmarDiarista);
		btConfirmar.setOnClickListener(btnConfirmarOnClickListener);
		btCancelar = (Button) layout.findViewById(R.id.buttonCancelarDiarista);
		btCancelar.setOnClickListener(btnCancelarOnClickListener);
		
		diarista = (Diarista) getArguments().getSerializable("diarista");
		
		List<Especialidade> especialidades = diarista.getEspecialidades();
		Especialidade especialidade = new Especialidade();
		String listaEspecialidades = "";
		for (int i=0;i<especialidades.size();i++){
			if (especialidades.get(i) != null){
				especialidade = especialidades.get(i);
				listaEspecialidades = listaEspecialidades + "* "+ especialidade.getNomeEspecialidade() + "\n";
			}else{
				break;
			}
		}
		txtNome.setText(diarista.getNome());
		txtCidade.setText(diarista.getCidade().getNomeCidade());
		txtEspecialidades.setText(listaEspecialidades);
		
		
		return layout;
	}
	
	public void gravarServico() throws ParseException{
		ServicoVO servico = new ServicoVO();
		usuario = db.listaUsuario();
		String date = data.getText().toString();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		Date data = new Date(format.parse(date).getTime());
		
		servico.setData(data);
		servico.setDescricao(descricao.getText().toString());
		servico.setDiarista(diarista);
		servico.setUsuario(usuario);
		//servico.setEnderecos(endereco.getText().toString());
		
	}
	private OnClickListener btnConfirmarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};

	private OnClickListener btnCancelarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};
	
	public void contratarServico(Servico servico) {
		if (Util.existeConexao(getActivity()))
			new HttpAsyncTask(Constantes.CONTRATAR_SERVICO, servico).execute();
	}
	
	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Servico serv;
		private String url;

		public HttpAsyncTask(String url, Servico serv) {
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

					JSONObject obj;
					try {
						obj = new JSONObject(result);
						System.out.println(obj);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
				}
		}			
	}
}
