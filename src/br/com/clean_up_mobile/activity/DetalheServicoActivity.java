package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.fragment.DetalheServicoFragment;
import br.com.clean_up_mobile.model.Servico;
import android.support.v7.app.ActionBarActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DetalheServicoActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_detalhe_servico);	
		Servico serv = (Servico)getIntent().getSerializableExtra("servico");		
		Bundle parametros = new Bundle();
		parametros.putSerializable("servico", serv);
		
		DetalheServicoFragment fragment = new DetalheServicoFragment();
		fragment.setArguments(parametros);
		
	    getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
		
	}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detalhe_servico,
					container, false);
			return rootView;
		}
}
