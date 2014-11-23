package br.com.clean_up_mobile.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.Duration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Cliente;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.DiaristaComCidade;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Perfil;
import br.com.clean_up_mobile.model.ServicoSimples;
import br.com.clean_up_mobile.model.StatusServico;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Mask;
import br.com.clean_up_mobile.util.Util;

public class DetalheServicoFragment extends Fragment implements OnClickListener {

	ServicoSimples serv = new ServicoSimples();
	Usuario usuarioLogado;
	TextView nome, telefone, nomeDiarista, data, endereco, descricao, valor,
			infoContratante;
	Button buttonCancel, buttonOk,btnCancelar;
	ImageView statusServico;
	LinearLayout llConfirmacao;
	StatusServico status;
	String mensagem;
	UsuarioDB db;
	Gson gson = new Gson();
	JsonParser parser = new JsonParser();
	JsonObject jsonEndereco, jsonCliente, jsonDiarista;
	Endereco objEndereco;
	Cliente objCliente;
	DiaristaComCidade objDiarista;

	public static DetalheServicoFragment novaInstancia(ServicoSimples servico) {
		Bundle args = new Bundle();
		args.putSerializable("servico", servico);

		DetalheServicoFragment f = new DetalheServicoFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_detalhe_servico,
				container, false);

		db = new UsuarioDB(getActivity().getApplicationContext());
		usuarioLogado = db.listaUsuario();

		serv = (ServicoSimples) getArguments().getSerializable("servico");

		// formatado a data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataServico = new Date(serv.getDataServico());
		String dataServicoFormatada = sdf.format(dataServico);

		Date hoje = new Date();
		
		

		jsonEndereco = (JsonObject) parser.parse(serv.getEndereco());
		jsonCliente = (JsonObject) parser.parse(serv.getCliente());
		jsonDiarista = (JsonObject) parser.parse(serv.getDiarista());

		objEndereco = gson.fromJson(jsonEndereco, Endereco.class);
		objCliente = gson.fromJson(jsonCliente, Cliente.class);
		objDiarista = gson.fromJson(jsonDiarista, DiaristaComCidade.class);

		endereco = (TextView) view.findViewById(R.id.textViewEnderecoCliente);
		descricao = (TextView) view.findViewById(R.id.textViewDescricao);
		nome = (TextView) view.findViewById(R.id.textViewNome);
		telefone = (TextView) view.findViewById(R.id.textViewTelefone);
		statusServico = (ImageView) view.findViewById(R.id.imageViewStatus);
		data = (TextView) view.findViewById(R.id.textViewData);
		infoContratante = (TextView) view.findViewById(R.id.textViewContratante);
		llConfirmacao = (LinearLayout) view.findViewById(R.id.linearLayoutConfirmacao); 
		btnCancelar = (Button)view.findViewById(R.id.buttonCancelar); 

		endereco.setText(objEndereco.getLogradouro());
		descricao.setText(serv.getDescricao());
		data.setText(dataServicoFormatada);

		// Altera a imagem de status do serviço
		if (serv.getStatus().equals("PENDENTE")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_pendente);

			if (hoje.compareTo(dataServico) > 0) {
				llConfirmacao.setVisibility(View.GONE);
			}

		} else if (serv.getStatus().equals("ATIVO")) {
			statusServico.setImageResource(R.drawable.ic_status_servico_ativo);

			if (hoje.compareTo(dataServico) >= 2) {
				btnCancelar.setVisibility(View.GONE);
			}

		} else if (serv.getStatus().equals("INATIVO")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_inativo);
		} else if (serv.getStatus().equals("CONCLUIDO")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_concluido);
		} else {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_sem_imagem);
		}
		
		
        //exibição de acordo com perfil
		if (usuarioLogado.getPerfil().equals("ROLE_DIARIST")) {
			nome.setText(objCliente.getNome());
			telefone.setText(objCliente.getTelefone());
			infoContratante.setText(R.string.tvContratanteCliente);
		} else {
			nome.setText(objDiarista.getNome());
			telefone.setText(objDiarista.getTelefone());
			infoContratante.setText(R.string.tvContratanteDiarista);
		}

		// // Botões
		// buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
		// buttonOk = (Button) view.findViewById(R.id.buttonOk);
		//
		// // if (serv.getStatus().equals("PENDENTE")) {
		// // buttonCancel.setText("Recusar");
		// // buttonOk.setText("Aceitar");
		// // } else if (serv.getStatus().equals("ATIVO")) {
		// // buttonCancel.setVisibility(View.GONE);
		// // } else {
		// // buttonCancel.setVisibility(View.GONE);
		// // buttonOk.setVisibility(View.GONE);
		// // }
		// } else {
		//
		// // configurando exibição para o cliente
		// nomeDiarista = (TextView)
		// view.findViewById(R.id.textViewNomeDiaristaDetalhe);
		// nomeDiarista.setText(serv.getDiarista().getNome());
		// }
		//
		// // valor = (TextView) view.findViewById(R.id.textViewValor);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonAceitar:
			if (buttonOk.getText().equals("Aceitar")) {
				confirmarServico(serv);
			} else {
				cancelaServico(serv);
			}
			break;
		case R.id.buttonRecusar:
			cancelaServico(serv);
			break;
		}
	}

	public void cancelaServico(ServicoSimples servico) {
		// somente 2 dias antes
		if (Util.existeConexao(getActivity().getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_CANCELASERVICO, servico)
					.execute();
	}

	public void confirmarServico(ServicoSimples servico) {
		// somente se a data do servico não tiver passado
		if (Util.existeConexao(getActivity().getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_CONFIRMASERVICO, servico)
					.execute();
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
		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, o);
		}

		@Override
		protected void onPostExecute(String result) {

			/*
			 * try { if (prgDialog.isShowing()) { prgDialog.dismiss(); } if
			 * (result != null) {
			 * 
			 * JSONObject obj = new JSONObject(result);
			 * 
			 * } } catch (JSONException e) {
			 * Util.criarToast(getActivity().getApplicationContext(),
			 * R.string.msgDeErroWebservice); }
			 */
		}
	}
}