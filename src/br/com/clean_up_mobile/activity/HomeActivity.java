package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Usuario;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends FragmentActivity implements
		OnItemClickListener, PanelSlideListener {

	//private SlidingPaneLayout mSlidingLayout;
	Usuario usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Colocando a Activity em tela cheia (opcional)
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_home);

		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		if (usuario.getPerfil().equals("ROLE_CLIENT")) {
			Intent homeClienteIntent = new Intent(
					getApplicationContext(), HomeClienteActivity.class);
			homeClienteIntent.putExtra("usuario", usuario);
			homeClienteIntent
			.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeClienteIntent);
			finish();
		} else if (usuario.getPerfil().equals("ROLE_DIARIST")) {
			Intent homeDiaristaIntent = new Intent(
					getApplicationContext(), HomeDiaristaActivity.class);
			homeDiaristaIntent.putExtra("usuario", usuario);
			homeDiaristaIntent
			.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(homeDiaristaIntent);
			finish();
		}

//		// centro
//		FragmentTransaction transactionCentro = getSupportFragmentManager()
//				.beginTransaction();
//		transactionCentro.replace(R.id.frameLayoutCentro, f);
//		transactionCentro.commit();
//
//		// menu
//		FragmentTransaction transactionMenu = getSupportFragmentManager()
//				.beginTransaction();
//		transactionMenu.replace(R.id.frameLayoutMenu, new MenuFragment());
//		transactionMenu.commit();
//
//		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
//		mSlidingLayout.setPanelSlideListener(this);
//
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		// TODO Tratar opções de Menu (ListView) aqui!
	}

	@Override
	public void onPanelClosed(View arg0) {
		// TODO Ao fechar o painel
	}

	@Override
	public void onPanelOpened(View arg0) {
		// TODO Ao abrir o painel
	}

	@Override
	public void onPanelSlide(View arg0, float arg1) {
		// TODO Enquanto o painel desliza
	}
}
