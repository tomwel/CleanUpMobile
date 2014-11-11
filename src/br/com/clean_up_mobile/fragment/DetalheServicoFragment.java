package br.com.clean_up_mobile.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Perfil;
import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.model.StatusServico;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;

public class DetalheServicoFragment extends Fragment implements OnClickListener{
	
	Servico serv = new Servico();
	Usuario usuarioLogado;
	TextView nomeCliente;
	TextView nomeDiarista;
	TextView data;
	TextView endereco;
	TextView descricao;
	TextView valor;
	Button buttonCancel;
	Button buttonOk;
	StatusServico status;
	String mensagem;
	UsuarioDB db;
	
	public static DetalheServicoFragment novaInstancia(Diarista diarista) {
		Bundle args = new Bundle();
		args.putSerializable("diarista", diarista);

		DetalheServicoFragment f = new DetalheServicoFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_detalhe_servico, container,
				false);
		
		nomeCliente = (TextView)view.findViewById(R.id.textViewNomeCliente);
		nomeDiarista = (TextView) view.findViewById(R.id.textViewNomeDiaristaDetalhe);
		data = (TextView) view.findViewById(R.id.textViewData);
		endereco = (TextView) view.findViewById(R.id.textViewEnderecoCliente);
		descricao = (TextView) view.findViewById(R.id.textViewDescricao);
		valor = (TextView) view.findViewById(R.id.textViewValor);
		buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
		buttonOk = (Button) view.findViewById(R.id.buttonOk);
		db = new UsuarioDB(getActivity().getApplicationContext());
		usuarioLogado = db.listaUsuario();
		
		serv = (Servico)getArguments().getSerializable("servico");
		
		nomeCliente.setText(serv.getCliente().getNome());
		nomeDiarista.setText(serv.getDiarista().getNome());
		//data.setText();
		endereco.setText(serv.getEndereco());
//		endereco.setText(serv.getEndereco().getRua()+" #"+ serv.getEndereco().getNumero() 
//				+" - "+ serv.getEndereco().getBairro()+ ", " + serv.getEndereco().getCidade());
		descricao.setText(serv.getDescricao());
		valor.setText(String.valueOf(serv.getValor()));
		
		if(usuarioLogado.getPerfil().equals(Perfil.ROLE_DIARIST)){
			if(serv.getStatus().equals("PENDENTE")){
				buttonCancel.setText("Recusar");
				buttonOk.setText("Aceitar");
			}else if(serv.getStatus().equals("ATIVO")){
				buttonCancel.setVisibility(View.GONE);
				buttonOk.setText("Cancelar");
			}else{
				buttonCancel.setVisibility(View.GONE);
				buttonOk.setVisibility(View.GONE);
			}
		}else{
			if(serv.getStatus().equals("PENDENTE")){
				buttonCancel.setVisibility(View.GONE);
				buttonOk.setVisibility(View.GONE);
			}else if(serv.getStatus().equals("ATIVO")){
				buttonCancel.setVisibility(View.GONE);
				buttonOk.setText("Cancelar");
			}else{
				buttonCancel.setVisibility(View.GONE);
				buttonOk.setVisibility(View.GONE);
			}
		}
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
        case R.id.buttonOk:
          if(buttonOk.getText().equals("Aceitar")){
        	  confirmarServico(serv);
          }else{
        	  cancelaServico(serv);
          }
          break;
        case R.id.buttonCancel:
        	cancelaServico(serv);
          break;
      }
	}
	
	public void cancelaServico(Servico servico) {
		// somente 2 dias antes
		if (Util.existeConexao(getActivity().getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_CANCELASERVICO, servico).execute();
	}
	
	public void confirmarServico(Servico servico) {
		//somente se a data do servico não tiver passado
		if (Util.existeConexao(getActivity().getApplicationContext()))
			new HttpAsyncTask(Constantes.POST_CONFIRMASERVICO, servico).execute();
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

			/*try {
				if (prgDialog.isShowing()) {
					prgDialog.dismiss();
				}
				if (result != null) {

					JSONObject obj = new JSONObject(result);
					
					}
				} catch (JSONException e) {
				Util.criarToast(getActivity().getApplicationContext(),
						R.string.msgDeErroWebservice);
				}*/
			}
		}
}