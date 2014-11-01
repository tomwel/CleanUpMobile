package br.com.clean_up_mobile.fragment;

import java.util.List;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.adapter.ServicoAdapter;
import br.com.clean_up_mobile.controller.ServicoController;
import br.com.clean_up_mobile.model.Servico;
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

public class ServicoFragment extends ListFragment {

	Task mTask;
	List<Servico> mServicos;
	ProgressBar progress;
	TextView txtMensagem;
	Integer codigoUsuario = null;;
	String tipoUsuario = null; // 0 diarista 1 cliente
	boolean atualizarWeb = true;

	public ServicoFragment(boolean a) {
		atualizarWeb = a;
	}

	public ServicoFragment(boolean a, String tipo, int codigo) {
		atualizarWeb = a;
		codigoUsuario = codigo;
		tipoUsuario = tipo;
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_servico, container,
				false);
		progress = (ProgressBar) view.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) view.findViewById(R.id.textViewLoading);
		return view;
	}

	private void mostrarProgress() {
		progress.setVisibility(View.VISIBLE);
		txtMensagem.setVisibility(View.VISIBLE);
		txtMensagem.setText(R.string.carregando);
	}

	private void atualizarLista() {
		ServicoAdapter adapter = new ServicoAdapter(getActivity(), mServicos);
		setListAdapter(adapter);
	}

	private void pegarLista() {

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {

			mTask = new Task();
			mTask.execute();
		}
	}

	class Task extends AsyncTask<Void, Void, List<Servico>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgress();
		}

		@Override
		protected List<Servico> doInBackground(Void... params) {
			try {
				
				ServicoController st = new ServicoController(getActivity());
				return st.pegarListaServico(atualizarWeb, tipoUsuario, codigoUsuario);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Servico> result) {
			super.onPostExecute(result);
			if (result != null) {
				mServicos = result;
				atualizarLista();
				txtMensagem.setVisibility(View.GONE);
			} else {
				txtMensagem.setText(R.string.conexao);
			}
			progress.setVisibility(View.GONE);
		}
	}

}
