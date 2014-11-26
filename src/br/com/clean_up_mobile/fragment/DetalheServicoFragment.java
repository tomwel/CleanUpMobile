package br.com.clean_up_mobile.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.R.drawable;
import br.com.clean_up_mobile.activity.ClassificaServicoActivity;
import br.com.clean_up_mobile.db.DiaristaFavoritaDB;
import br.com.clean_up_mobile.db.EspecialidadeDB;
import br.com.clean_up_mobile.db.ServicoDB;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Cliente;
import br.com.clean_up_mobile.model.DiaristaComCidade;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.ServicoSimples;
import br.com.clean_up_mobile.model.StatusServico;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;

public class DetalheServicoFragment extends Fragment implements OnClickListener {

	ServicoSimples serv = new ServicoSimples();
	Usuario usuarioLogado;
	TextView nome, telefone, nomeDiarista, data, endereco, descricao, valor,
			infoContratante;
	Button buttonCancel, buttonOk, btnCancelar;
	ImageView statusServico;
	LinearLayout llConfirmacao;
	StatusServico status;
	String mensagem;
	UsuarioDB db;
	ServicoDB dbServico;
	EspecialidadeDB dbEspecialidade;
	DiaristaFavoritaDB dbDiaristaFav;
	Gson gson = new Gson();
	JsonParser parser = new JsonParser();
	JsonObject jsonEndereco, jsonCliente, jsonDiarista;
	Endereco objEndereco;
	Cliente objCliente;
	DiaristaComCidade objDiarista;
	ImageButton btnFavorito;
	Button btnAvaliar;

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
		dbDiaristaFav = new DiaristaFavoritaDB(getActivity());
		dbEspecialidade = new EspecialidadeDB(getActivity());
		dbServico = new ServicoDB(getActivity());
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
		objDiarista.favorito = dbDiaristaFav.favorito(objDiarista);
		List<Especialidade> especialidades = objDiarista.getEspecialidades();
		Especialidade especialidade = new Especialidade();
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
							objDiarista.getCodigo(),
							especialidade.getCodigoEspecialidade());
				}
				if (dbEspecialidade.pegarEspecialidades(objDiarista.getCodigo()).size() < especialidades.size()){
					dbEspecialidade.inserirRelacionamentoEspecialidadeDiarista(
							objDiarista.getCodigo(),
							especialidade.getCodigoEspecialidade());
				}
			} else {
				break;
			}
		}
		endereco = (TextView) view.findViewById(R.id.textViewEnderecoCliente);
		descricao = (TextView) view.findViewById(R.id.textViewDescricao);
		nome = (TextView) view.findViewById(R.id.textViewNome);
		telefone = (TextView) view.findViewById(R.id.textViewTelefone);
		statusServico = (ImageView) view.findViewById(R.id.imageViewStatus);
		data = (TextView) view.findViewById(R.id.textViewData);
		infoContratante = (TextView) view
				.findViewById(R.id.textViewContratante);
		llConfirmacao = (LinearLayout) view
				.findViewById(R.id.linearLayoutConfirmacao);
		btnCancelar = (Button) view.findViewById(R.id.buttonCancelar);
		btnAvaliar = (Button) view.findViewById(R.id.buttonAvaliarServico);
		btnAvaliar.setOnClickListener(btnAvaliarOnClickListener);
		btnFavorito = (ImageButton) view.findViewById(R.id.imageButtonFavorito);
		btnFavorito.setOnClickListener(btnFavoritoOnClickListener);
		if (objDiarista.favorito) {
			btnFavorito.setImageResource(drawable.favorite_delete);
		} else {
			btnFavorito.setImageResource(drawable.favorite_add);
		}

		ImageButton btnVerMapa = (ImageButton) view
				.findViewById(R.id.imageButtonVerMapa);
		btnVerMapa.setOnClickListener(btnVerMapaOnClickListener);

		endereco.setText(objEndereco.getLogradouro());
		descricao.setText(serv.getDescricao());
		data.setText(dataServicoFormatada);

		// Altera a imagem de status do serviço
		if (serv.getStatus().equals("PENDENTE")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_pendente);
			btnAvaliar.setVisibility(view.GONE);
			if (hoje.compareTo(dataServico) > 0) {
				llConfirmacao.setVisibility(View.GONE);
			}

		} else if (serv.getStatus().equals("ACEITO")) {
			statusServico.setImageResource(R.drawable.ic_status_servico_ativo);

			if (hoje.compareTo(dataServico) >= 2) {
				btnCancelar.setVisibility(View.GONE);
			}

		} else if (serv.getStatus().equals("CANCELAR")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_inativo);
			btnAvaliar.setVisibility(view.GONE);
		} else if (serv.getStatus().equals("CONCLUIDO")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_concluido);
			btnAvaliar.setVisibility(view.GONE);
		} else {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_sem_imagem);
		}

		// exibição de acordo com perfil
		if (usuarioLogado.getPerfil().equals("ROLE_DIARIST")) {
			nome.setText(objCliente.getNome());
			telefone.setText(objCliente.getTelefone());
			infoContratante.setText(R.string.tvContratanteCliente);
			btnFavorito.setVisibility(view.GONE);
		} else {
			nome.setText(objDiarista.getNome());
			telefone.setText(objDiarista.getTelefone());
			infoContratante.setText(R.string.tvContratanteDiarista);
			btnVerMapa.setVisibility(view.GONE);
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
	private OnClickListener btnAvaliarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent classificarIntent = new Intent(
					getActivity(), ClassificaServicoActivity.class);
			classificarIntent.putExtra("servico", serv);
			startActivity(classificarIntent);
		}
	};

	private OnClickListener btnFavoritoOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (objDiarista.favorito) {
				dbDiaristaFav.excluir(objDiarista);
				Toast.makeText(getActivity(),
						"Diarista removida dos favoritos", Toast.LENGTH_SHORT)
						.show();
				btnFavorito.setImageResource(drawable.favorite_add);
				objDiarista.favorito = false;
			} else {
				dbDiaristaFav.inserir(objDiarista);
				Toast.makeText(getActivity(),
						"Diarista Adicionada aos favoritos", Toast.LENGTH_SHORT)
						.show();
				btnFavorito.setImageResource(drawable.favorite_delete);
				objDiarista.favorito = true;
			}
			if (getActivity() instanceof DiaristaNosFavoritos) {
				((DiaristaNosFavoritos) getActivity())
						.diaristaAdicionadaAoFavorito(objDiarista);
			}
		}
	};

	interface DiaristaNosFavoritos {
		void diaristaAdicionadaAoFavorito(DiaristaComCidade diarista);
	}

	private OnClickListener btnVerMapaOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String lat = objEndereco.getLat().toString();
			String lng = objEndereco.getLng().toString();
			String uri = "http://maps.google.com/maps?&daddr=" + lat + ","
					+ lng;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			intent.setClassName("com.google.android.apps.maps",
					"com.google.android.maps.MapsActivity");
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException ex) {
				try {
					Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW,
							Uri.parse(uri));
					startActivity(unrestrictedIntent);
				} catch (ActivityNotFoundException innerEx) {
					Util.criarToast(getActivity(),
							"Por favor, instale o google maps para ver o mapa!");
				}
			}
		}
	};

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