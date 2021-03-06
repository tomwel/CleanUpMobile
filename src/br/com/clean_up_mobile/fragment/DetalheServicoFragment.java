package br.com.clean_up_mobile.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.R.drawable;
import br.com.clean_up_mobile.activity.HomeDiaristaActivity;
import br.com.clean_up_mobile.activity.PerfilActivity;
import br.com.clean_up_mobile.db.DiaristaFavoritaDB;
import br.com.clean_up_mobile.db.EspecialidadeDB;
import br.com.clean_up_mobile.db.ServicoDB;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Cliente;
import br.com.clean_up_mobile.model.DiaristaComCidade;
import br.com.clean_up_mobile.model.DiaristaServico;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.model.ServicoSimples;
import br.com.clean_up_mobile.model.StatusServico;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.ClassificacaoVO;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DetalheServicoFragment extends Fragment {

	ServicoSimples serv = new ServicoSimples();
	Usuario usuarioLogado;
	ClassificacaoVO classificacaoVo;
	TextView nome;
	TextView telefone;
	TextView nomeDiarista;
	TextView data;
	TextView endereco;
	TextView descricao;
	TextView valor;
	TextView infoContratante;
	Button buttonCancelar;
	Button buttonAceitar;
	Button buttonRecusar;
	ImageView statusServico;
	LinearLayout llConfirmacao;
	LinearLayout llAvaliacao;
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
	ImageView imgCliente;
	ProgressDialog prgDialog;
	Button btnAvaliar;
	RatingBar rbClassificacaoDiarista;
	TextView tvComentario;

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

		long DAY = 24L * 60L * 60L * 1000L;
		long diferencaData = ((dataServico.getTime() - hoje.getTime()) / DAY);

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
				if (dbEspecialidade
						.pegarEspecialidades(objDiarista.getCodigo()).size() < especialidades
						.size()) {
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
		imgCliente = (ImageView) view
				.findViewById(R.id.imageViewClienteServico);
		telefone = (TextView) view.findViewById(R.id.textViewTelefone);
		statusServico = (ImageView) view.findViewById(R.id.imageViewStatus);
		data = (TextView) view.findViewById(R.id.textViewData);
		infoContratante = (TextView) view
				.findViewById(R.id.textViewContratante);
		llConfirmacao = (LinearLayout) view
				.findViewById(R.id.linearLayoutConfirmacao);
		llAvaliacao = (LinearLayout) view
				.findViewById(R.id.linearLayoutAvaliacao);
		buttonCancelar = (Button) view.findViewById(R.id.buttonCancelar);
		buttonCancelar.setOnClickListener(btnCancelarOnClickListener);
		buttonAceitar = (Button) view.findViewById(R.id.buttonAceitar);
		buttonAceitar.setOnClickListener(btnAceitarOnClickListener);
		buttonRecusar = (Button) view.findViewById(R.id.buttonRecusar);
		buttonRecusar.setOnClickListener(btnCancelarOnClickListener);
		btnAvaliar = (Button) view.findViewById(R.id.buttonAvaliarServico);
		btnAvaliar.setOnClickListener(btnAvaliarOnClickListener);
		btnFavorito = (ImageButton) view.findViewById(R.id.imageButtonFavorito);
		btnFavorito.setOnClickListener(btnFavoritoOnClickListener);
		rbClassificacaoDiarista = (RatingBar) view
				.findViewById(R.id.ratingBarClassificacaoDiarista);
		tvComentario = (TextView) view.findViewById(R.id.textViewComentario);

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

		if (diferencaData > 1
				&& usuarioLogado.getPerfil().equals("ROLE_DIARIST")) {
			llConfirmacao.setVisibility(View.VISIBLE);
		} else if (serv.getStatus().equals("ACEITO")) {
			statusServico.setImageResource(R.drawable.ic_status_servico_ativo);
			if(usuarioLogado.getPerfil().equals("ROLE_CLIENT")){
			}
			if (diferencaData >= 2) {
				btnAvaliar.setVisibility(view.VISIBLE);
			}else{
				buttonCancelar.setVisibility(View.VISIBLE);
			}
		} else if (serv.getStatus().equals("CANCELAR")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_inativo);
		} else if (serv.getStatus().equals("CONCLUIDO")) {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_concluido);
			if (serv.getAvaliacao() != 0 && serv.getComentario() != null
					&& usuarioLogado.getPerfil().equals("ROLE_CLIENT")) {
				llAvaliacao.setVisibility(view.VISIBLE);
				rbClassificacaoDiarista.setRating(serv.getAvaliacao());
				tvComentario.setText(serv.getComentario());
			}
		} else {
			statusServico
					.setImageResource(R.drawable.ic_status_servico_sem_imagem);
		}

		// exibi��o de acordo com perfil
		if (usuarioLogado.getPerfil().equals("ROLE_DIARIST")) {
			nome.setText(objCliente.getNome());
			telefone.setText(objCliente.getTelefone());
			infoContratante.setText(R.string.tvContratanteCliente);
			btnFavorito.setVisibility(view.GONE);
			btnAvaliar.setVisibility(view.GONE);
		} else {
			nome.setText(objDiarista.getNome());
			telefone.setText(objDiarista.getTelefone());
			infoContratante.setText(R.string.tvContratanteDiarista);
			btnVerMapa.setVisibility(view.GONE);
		}
		return view;
	}

	private OnClickListener btnAvaliarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

			View promptView = layoutInflater.inflate(
					R.layout.fragment_classifica_servico, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

			// set prompts.xml to be the layout file of the alertdialog builder

			alertDialogBuilder.setView(promptView);

			final RatingBar nota = (RatingBar) promptView
					.findViewById(R.id.ratingBarClassificaServico);
			final EditText comentario = (EditText) promptView
					.findViewById(R.id.editTextComentario);
			comentario.clearFocus();
			// setup a dialog window

			alertDialogBuilder
					.setTitle(R.string.classificaServico)
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int id) {

									// get user input and set it to result
									// dbServicoDB = new
									// ServicoDB(getActivity());
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
									servico.setDataServico(serv
											.getDataServico());
									servico.setDescricao(serv.getDescricao());
									servico.setStatus(serv.getStatus());
									servico.setEndereco(endereco);
									classificacaoVo = new ClassificacaoVO();
									classificacaoVo.setComentario(comentario
											.getText().toString());
									classificacaoVo.setPontuacao(nota
											.getRating());
									classificacaoVo.setServico(servico);

									if (Util.existeConexao(getActivity()
											.getApplicationContext())) {
										new ClassificarHttpAsyncTask(
												Constantes.POST_CLASSIFICASERVICO,
												classificacaoVo).execute();
										btnAvaliar.setVisibility(View.GONE);
									}
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}); // create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();

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

	private OnClickListener btnCancelarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// somente 2 dias antes
			if (Util.existeConexao(getActivity().getApplicationContext())) {
				String url = Constantes.POST_ATUALIZAR + "recusar/"
						+ serv.getCodServico();
				new HttpAsyncTask(url).execute();
			}
		}
	};

	private OnClickListener btnAceitarOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// somente se a data do servico n�o tiver passado
			if (Util.existeConexao(getActivity().getApplicationContext())) {
				String url = Constantes.POST_ATUALIZAR + "aceitar/"
						+ serv.getCodServico();
				new HttpAsyncTask(url).execute();
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

	private void mostrarProgressDialog(String texto) {
		prgDialog = new ProgressDialog(getActivity());
		prgDialog.setMessage(texto);
		prgDialog.setTitle("Cleanup");
		prgDialog.setCancelable(true);
		prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		prgDialog.show();
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {

		private String url;

		public HttpAsyncTask(String u) {
			this.url = u;
		}

		@Override
		protected void onPreExecute() {
			mostrarProgressDialog("Enviando informa��o...");
		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url);
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
						Util.criarToast(getActivity().getApplicationContext(),
								"Informa��es Atualizada.");
						Intent it = new Intent(getActivity(),
								HomeDiaristaActivity.class);
						startActivity(it);
						getActivity().finish();
					} else {
						Util.criarToast(getActivity().getApplicationContext(),
								R.string.msgDeErroWebservice);
					}
				} else {

					Util.criarToast(getActivity().getApplicationContext(),
							R.string.msgDeErroWebservice);
				}
			} catch (JSONException e) {
				Util.criarToast(getActivity().getApplicationContext(),
						R.string.msgDeErroWebservice);
			}

		}
	}

	private class ClassificarHttpAsyncTask extends
			AsyncTask<Void, Void, String> {

		private Object o;
		private String url;

		public ClassificarHttpAsyncTask(String url, Object o) {
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
					Util.criarToast(getActivity(), R.string.msgServicoAvaliacao);
				} catch (Exception e) {
					Util.criarToast(getActivity(), R.string.msgDeErroWebservice);
					e.printStackTrace();
				}
			}
		}
	}

	public static Bitmap decodeBase64(String input) {
		int inicio;
		int fim;
		inicio = input.indexOf(",");
		fim = input.length();
		String imgBase64 = input.substring(inicio + 1, fim);
		byte[] decodedByte = Base64.decode(imgBase64, Base64.DEFAULT);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}
