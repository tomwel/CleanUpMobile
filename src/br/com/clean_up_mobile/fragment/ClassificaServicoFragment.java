package br.com.clean_up_mobile.fragment;

import java.text.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Cliente;
import br.com.clean_up_mobile.model.DiaristaComCidade;
import br.com.clean_up_mobile.model.DiaristaServico;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.model.ServicoSimples;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.ClassificacaoVO;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class ClassificaServicoFragment extends Fragment implements
		OnClickListener {

	ServicoSimples serv;
	//ServicoDB dbServicoDB;
	ClassificacaoVO classificacaoVo;
	TextView nomediarista;
	TextView enderecoservico;
	RatingBar nota;
	EditText comentario;
	ImageButton btnAvaliar;
	Gson gson = new Gson();
	Endereco objEndereco;
	DiaristaComCidade objDiarista;
	Cliente objCliente;
	JsonParser parser = new JsonParser();
	JsonObject jsonEndereco, jsonDiarista, jsonCliente;

	public static ClassificaServicoFragment novaInstancia(ServicoSimples servico) {
		Bundle args = new Bundle();
		args.putSerializable("servico", servico);
		ClassificaServicoFragment f = new ClassificaServicoFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_classifica_servico,
				container, false);

		nomediarista = (TextView) view
				.findViewById(R.id.textViewNomeDiaristaClassificada);
		enderecoservico = (TextView) view
				.findViewById(R.id.textViewEnderecoServico);
		nota = (RatingBar) view.findViewById(R.id.ratingBarClassificaServico);
		comentario = (EditText) view.findViewById(R.id.editTextComentario);
		serv = (ServicoSimples) getArguments().getSerializable("servico");
		jsonEndereco = (JsonObject) parser.parse(serv.getEndereco());
		jsonDiarista = (JsonObject) parser.parse(serv.getDiarista());
		jsonCliente = (JsonObject) parser.parse(serv.getCliente());
		objEndereco = gson.fromJson(jsonEndereco, Endereco.class);
		objDiarista = gson.fromJson(jsonDiarista, DiaristaComCidade.class);
		objCliente = gson.fromJson(jsonCliente, Cliente.class);
		
		nomediarista.setText(objDiarista.getNome());
		enderecoservico.setText(objEndereco.getLogradouro());
		Button btnClassificar = (Button) view
				.findViewById(R.id.buttonClassifica);
		btnClassificar.setOnClickListener(btnClassificarOnClickListener);
		return view;
	}

	private OnClickListener btnClassificarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//dbServicoDB = new ServicoDB(getActivity());
			Servico servico = new Servico();
			DiaristaServico diarista = new DiaristaServico();
			Cliente cliente = new Cliente();
			Endereco endereco = new Endereco();
			cliente.setCodigo(objCliente.getCodigo());
			diarista.setCodigo(objDiarista.getCodigo());
			endereco.setCodigo(objEndereco.getCodigo());
			servico.setCliente(cliente);
			servico.setDiarista(diarista);
			servico.setCodServico(serv.getCodServico());
			servico.setDataServico(serv.getDataServico());
			servico.setDescricao(serv.getDescricao());
			servico.setStatus(serv.getStatus());
			servico.setEndereco(endereco);
			classificacaoVo = new ClassificacaoVO();
			classificacaoVo.setComentario(comentario.getText().toString());
			classificacaoVo.setPontuacao(nota.getNumStars());
			classificacaoVo.setServico(servico);
			
			if (Util.existeConexao(getActivity().getApplicationContext())) {
				new HttpAsyncTask(Constantes.POST_CLASSIFICASERVICO,
						classificacaoVo).execute();
			}
		}
	};

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private Object o;
		private String url;

		public HttpAsyncTask(String url, Object o) {
			this.o = o;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, o);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

				try {
					Util.criarToast(getActivity(),
							R.string.msgServicoSolicitado);
					getActivity().finish();
				} catch (Exception e) {
					Util.criarToast(getActivity(), R.string.msgDeErroWebservice);
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}