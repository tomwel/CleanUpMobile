package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.UsuarioDB;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	UsuarioDB db;
	Usuario usuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 5 seconds
					sleep(5 * 1000);

					// After 5 seconds redirect to another intent
					db = new UsuarioDB(getApplicationContext());
					usuario = db.listaUsuario();
					if (usuario.getEmail() != null) {
						if (usuario.getPerfil().equals("ROLE_CLIENT")) {
							navigatetoHomeClientActivity();
						} else if (usuario.getPerfil().equals("ROLE_DIARIST")) {
							navigatetoHomeDiaristActivity();
						}
					} else {
						Intent homeLoginIntent = new Intent(getApplicationContext(),
								LoginActivity.class);
						homeLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(homeLoginIntent);
						finish();
					}
				} catch (Exception e) {

				}
			}
		};
		// start thread
		background.start();	
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

	}

	public void navigatetoHomeClientActivity() {
		Intent homeClientIntent = new Intent(getApplicationContext(),
				HomeClientActivity.class);
		homeClientIntent.putExtra("usuario", usuario);
		homeClientIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeClientIntent);
		finish();
	}

	public void navigatetoHomeDiaristActivity() {
		Intent homeDiaristIntent = new Intent(getApplicationContext(),
				HomeDiaristActivity.class);
		homeDiaristIntent.putExtra("usuario", usuario);
		homeDiaristIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(homeDiaristIntent);
		finish();
	}
}
