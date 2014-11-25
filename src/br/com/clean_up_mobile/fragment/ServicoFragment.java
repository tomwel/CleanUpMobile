package br.com.clean_up_mobile.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.activity.SwitchingActivity;
import br.com.clean_up_mobile.adapter.ServicoAdapter;
import br.com.clean_up_mobile.controller.ServicoController;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.ServicoSimples;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.Util;

public class ServicoFragment extends ListFragment implements OnRefreshListener {

	Task mTask;
	AtualizaManualTask atualizaManualTask;
	List<ServicoSimples> mServicos;
	ProgressBar progress;
	TextView txtMensagem, txtAviso;
	Integer codigoUsuario = 0;
	String tipoUsuario, where = ""; // 0 diarista 1 cliente
	boolean atualizarWeb = true;
	boolean aparecerAviso = false;
	UsuarioDB db;
	Usuario usuario;
	Context context;
	SwipeRefreshLayout swipeLayout;

	public ServicoFragment(boolean a, Context c, String w) {

		db = new UsuarioDB(c);
		usuario = db.listaUsuario();

		context = c;

		atualizarWeb = a;
		codigoUsuario = usuario.getId();
		tipoUsuario = usuario.getPerfil();
		where = w;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (mServicos != null) {
			txtMensagem.setVisibility(View.GONE);
			progress.setVisibility(View.GONE);
			atualizarLista();

		} else {
			if (mTask != null && mTask.getStatus() == Status.RUNNING) {
				mostrarProgress();

			} else {
				pegarLista();
			}
		}
		setRetainInstance(true);

		ListView listView = getListView();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ServicoSimples s = mServicos.get(position);

				Intent it = new Intent(view.getContext(),
						SwitchingActivity.class);
				it.putExtra("servico", (Serializable) s);
				startActivity(it);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_servico, container,
				false);
		progress = (ProgressBar) view.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) view.findViewById(R.id.textViewLoading);
		txtAviso = (TextView) view.findViewById(R.id.textAviso);

		swipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		return view;
	}

	@Override
	public void onRefresh() {
		if (Util.existeConexao(context))
			new AtualizaManualTask().execute();
	}

	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText(R.string.carregando);
	}

	private void atualizarLista() {
		if (mServicos != null) {
			ServicoAdapter adapter = new ServicoAdapter(getActivity(),
					mServicos);
			setListAdapter(adapter);
		}
	}

	private void pegarLista() {
		if (Util.existeConexao(context))
			new Task().execute();
	}

	class Task extends AsyncTask<Void, Void, List<ServicoSimples>> {

		@Override
		protected void onPreExecute() {
			mostrarProgress();
		}

		@Override
		protected List<ServicoSimples> doInBackground(Void... params) {
			List<ServicoSimples> listServicos = new ArrayList<ServicoSimples>();
			ServicoController sc = new ServicoController(getActivity());

			try {
				if (atualizarWeb) {
					if (!sc.atualizarBancoLocal(tipoUsuario, codigoUsuario)) {
						aparecerAviso = true;
					} else {
						aparecerAviso = false;
					}
				}
				listServicos = sc.pegarListaServicosLocal(where);
			} catch (Exception e) {
				aparecerAviso = true;
				listServicos = sc.pegarListaServicosLocal(where);
			}
			return listServicos;
		}

		@Override
		protected void onPostExecute(List<ServicoSimples> result) {
			if (result != null) {
				mServicos = result;
				atualizarLista();
				txtMensagem.setVisibility(View.GONE);
				txtAviso.setVisibility(View.VISIBLE);
			} else {
				txtMensagem.setText(R.string.conexao);
			}
			progress.setVisibility(View.GONE);
			if (aparecerAviso) {
				txtAviso.setVisibility(View.VISIBLE);
				Util.criarToast(context, R.string.msgDeErroWebservice);
			} else {
				txtAviso.setVisibility(View.GONE);
			}
		}
	}

	class AtualizaManualTask extends
			AsyncTask<Void, Void, List<ServicoSimples>> {

		@Override
		protected List<ServicoSimples> doInBackground(Void... params) {
			List<ServicoSimples> listServicos = new ArrayList<ServicoSimples>();
			ServicoController sc = new ServicoController(getActivity());

			try {
				if (!sc.atualizarBancoLocal(tipoUsuario, codigoUsuario)) {
					aparecerAviso = true;
				} else {
					aparecerAviso = false;
				}
				listServicos = sc.pegarListaServicosLocal(where);
			} catch (Exception e) {
				aparecerAviso = true;
				listServicos = sc.pegarListaServicosLocal(where);
			}
			return listServicos;
		}

		@Override
		protected void onPostExecute(List<ServicoSimples> result) {
			if (result != null) {
				mServicos = result;
				atualizarLista();
				txtMensagem.setVisibility(View.GONE);
				txtAviso.setVisibility(View.VISIBLE);
			} else {
				txtMensagem.setText(R.string.conexao);
			}
			swipeLayout.setRefreshing(false);
			if (aparecerAviso) {
				txtAviso.setVisibility(View.VISIBLE);
				Util.criarToast(context, R.string.msgDeErroWebservice);
			} else {
				txtAviso.setVisibility(View.GONE);
			}
		}
	}
}