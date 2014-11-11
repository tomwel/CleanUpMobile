package br.com.clean_up_mobile.fragment;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class ClassificaServicoFragment extends Fragment implements OnClickListener{
	
	Servico serv;
	TextView nomediarista;
	TextView enderecoservico;
	RatingBar nota;
	EditText comentario;
	
	public static DetalheServicoFragment novaInstancia(Servico servico) {
		Bundle args = new Bundle();
		args.putSerializable("servico", servico);

		DetalheServicoFragment f = new DetalheServicoFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_classifica_servico, container,
				false);
		
		nomediarista = (TextView)view.findViewById(R.id.textViewNomeDiaristaClassificada);
		enderecoservico = (TextView)view.findViewById(R.id.textViewEnderecoServico);
		nota = (RatingBar)view.findViewById(R.id.ratingBarClassificaServico);
		comentario = (EditText)view.findViewById(R.id.editTextComentario);
		
		serv = (Servico)getArguments().getSerializable("servico");
		
		nomediarista.setText(serv.getDiarista().getNome());
		enderecoservico.setText(serv.getEndereco());
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		serv.set
		if (Util.existeConexao(getActivity().getApplicationContext())){
			new HttpAsyncTask(Constantes.POST_CLASSIFICASERVICO, serv).execute();
		}
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
			}
		}
	
}