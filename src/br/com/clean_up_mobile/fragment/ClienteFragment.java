package br.com.clean_up_mobile.fragment;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.WebService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClienteFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cliente, container,
				false);
		getDiaristas();
		return view;
	}

	public void getDiaristas() {
		// if (Util.existeConexao(getActivity()))
		new HttpAsyncTask(Constantes.GET_DIARISTAS).execute();
	}

	private class HttpAsyncTask extends AsyncTask<Void, Void, String> {
		private String url;

		public HttpAsyncTask(String url) {
			this.url = url;
		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url);
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println(result);
		}
	}
}
