package br.com.clean_up_mobile.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.activity.OnClickDiarista;
import br.com.clean_up_mobile.adapter.DiaristasAdapter;
import br.com.clean_up_mobile.db.EspecialidadeDB;
import br.com.clean_up_mobile.fragment.ServicoFragment.AtualizaManualTask;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.task.DiaristasHttp;
import br.com.clean_up_mobile.util.Util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ClienteFragment extends ListFragment implements
		SearchView.OnQueryTextListener, OnRefreshListener {

	DiaristasTask mTask;
	List<Diarista> mDiaristas;
	ProgressBar progress;
	TextView txtMensagem;
	private SearchView mSearchView;
	EspecialidadeDB dbEspecialidade;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dbEspecialidade = new EspecialidadeDB(getActivity());
		if (mDiaristas != null) {
			txtMensagem.setVisibility(View.GONE);
			progress.setVisibility(View.GONE);
			refreshList();

		} else {
			if (mTask != null && mTask.getStatus() == Status.RUNNING) {
				mostrarProgress();

			} else {
				iniciarDownload();
			}
		}
		setRetainInstance(true);
	}

	private void iniciarDownload() {

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {

			mTask = new DiaristasTask();
			mTask.execute();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cliente, container,
				false);
		progress = (ProgressBar) view.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) view.findViewById(R.id.textViewLoading);
		mSearchView = (SearchView) view.findViewById(R.id.searchview);
		mSearchView.setQueryHint("Procurar Diarista");
		mSearchView.setIconifiedByDefault(false);
		mSearchView.setOnQueryTextListener(this);
		mSearchView.clearFocus();
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (getActivity() instanceof OnClickDiarista) {
			((OnClickDiarista) getActivity()).clickedDiarista(mDiaristas
					.get(position));
		}
	}

	@Override
	public boolean onQueryTextChange(String text) {
		ArrayList<Diarista> list = new ArrayList<Diarista>();
		Diarista diarista = null;
		int pos = 0;
		List<String> especialidades = new ArrayList<String>();
		if (mDiaristas != null) {
			while (pos < mDiaristas.size()) {
				diarista = mDiaristas.get(pos);
				for (int i = 0; i < diarista.getEspecialidades().size(); i++) {
					especialidades.add(diarista.getEspecialidades().get(i)
							.getNomeEspecialidade().toLowerCase());
				}
				if (diarista.getNome().toLowerCase()
						.contains(text.toLowerCase())
						|| diarista.getCidade().toLowerCase()
								.contains(text.toLowerCase())
						|| especialidades.contains(text.toLowerCase())) {
					list.add(diarista);
				}
				pos++;
			}

			DiaristasAdapter adapter = new DiaristasAdapter(getActivity(), list);
			setListAdapter(adapter);

			return true;
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String text) {

		return false;
	}

	@Override
	public void onRefresh() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {

			mTask = new DiaristasTask();
			mTask.execute();
		}

	}

	private void refreshList() {
		DiaristasAdapter adapter = new DiaristasAdapter(getActivity(),
				mDiaristas);
		setListAdapter(adapter);
	}

	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText(R.string.carregando);
	}

	class DiaristasTask extends AsyncTask<Void, Void, List<Diarista>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgress();
		}

		@Override
		protected List<Diarista> doInBackground(Void... params) {
			try {
				return DiaristasHttp.retrieveDiaristas();
			} catch (IOException i) {
				i.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Diarista> result) {
			super.onPostExecute(result);
			if (result != null) {
				mDiaristas = result;
				refreshList();
				txtMensagem.setVisibility(View.GONE);
			} else {
				Util.criarToast(getActivity(), R.string.msgDeErroWebservice);
			}
			progress.setVisibility(View.GONE);
		}
	}
}
