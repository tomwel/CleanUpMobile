package br.com.clean_up_mobile.fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.adapter.ServicoAdapter;
import br.com.clean_up_mobile.controller.ServicoController;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.Util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ServicoFragment extends ListFragment {

	Task mTask;
	List<Servico> mServicos;
	ProgressBar progress;
	TextView txtMensagem, txtAviso;
	Integer codigoUsuario = 0;
	String tipoUsuario, where = ""; // 0 diarista 1 cliente
	boolean atualizarWeb = true;
	boolean aparecerAviso = false;
	UsuarioDB db;
	Usuario usuario;
	Context context;

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_servico, container,
				false);
		progress = (ProgressBar) view.findViewById(R.id.progressBar1);
		txtMensagem = (TextView) view.findViewById(R.id.textViewLoading);
		txtAviso = (TextView) view.findViewById(R.id.textAviso);
		return view;
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
			mostrarProgress();
		}

		@Override
		protected List<Servico> doInBackground(Void... params) {
			List<Servico> listServicos = new ArrayList<Servico>();
			ServicoController sc = new ServicoController(getActivity());

			try {
				if (atualizarWeb) {
					if (!sc.atualizarBancoLocal(tipoUsuario, codigoUsuario)) {
						aparecerAviso = true;
					} else {
						aparecerAviso = false;
					}
				}
				listServicos = sc.pegarListaServicosLocal();
			} catch (Exception e) {
				aparecerAviso = true;
				listServicos = sc.pegarListaServicosLocal();
			}
			return listServicos;
		}

		@Override
		protected void onPostExecute(List<Servico> result) {
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

}
