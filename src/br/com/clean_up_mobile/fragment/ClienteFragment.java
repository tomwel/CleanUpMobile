package br.com.clean_up_mobile.fragment;

import java.util.List;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.adapter.DiaristasAdapter;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.task.DiaristasHttp;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ClienteFragment extends ListFragment {

	DiaristasTask mTask;
	List<Diarista> mDiaristas;
	ProgressBar progress;
	TextView txtMensagem;
	
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

		/*} else {
			progress.setVisibility(View.GONE);
			txtMensagem.setVisibility(View.VISIBLE);
			txtMensagem.setText("Sem conex�o com a Internet");
			txtMensagem.setText(R.string.conexao);
			*/
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cliente, container,
				false);

		progress = (ProgressBar) view.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) view.findViewById(R.id.textViewCidadeListDiarista);

		return view;
	}

	
	private void refreshList() {
		DiaristasAdapter adapter = new DiaristasAdapter(getActivity(), mDiaristas);
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