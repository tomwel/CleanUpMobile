package br.com.clean_up_mobile.fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.activity.OnClickDiarista;
import br.com.clean_up_mobile.adapter.DiaristasAdapter;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.task.DiaristasHttp;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.ListFragment;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ClienteFragment extends ListFragment implements SearchView.OnQueryTextListener{

	DiaristasTask mTask;
	List<Diarista> mDiaristas;
	ProgressBar progress;
	TextView txtMensagem;
	private SearchView mSearchView;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (getActivity() instanceof OnClickDiarista) {
			((OnClickDiarista) getActivity()).clickedDiarista(mDiaristas.get(position));
		}
	}
	
	@Override
	public boolean onQueryTextChange(String text) {
		//System.out.println(text);
		ArrayList<Diarista> list = new ArrayList<Diarista>();  
        Diarista diarista = null;  
        int pos = 0;  
        while(pos< mDiaristas.size()){  
            diarista = mDiaristas.get(pos); 
            if(diarista.getNome().toLowerCase().contains(text.toLowerCase()) || diarista.getCidade().getNomeCidade().toLowerCase().contains(text.toLowerCase())){  
                list.add(diarista); 
            }  
            pos++;  
        }  
        DiaristasAdapter adapter = new DiaristasAdapter(getActivity(),
				list);
		setListAdapter(adapter);
		
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String text) {
		
		return false;
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
				txtMensagem.setText(R.string.conexao);
			}
			progress.setVisibility(View.GONE);
		}
	}
}
