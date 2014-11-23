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
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_servico, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detalhe_servico,
					container, false);
			return rootView;
		}
}
