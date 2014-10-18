package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.model.Usuario;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
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
					sleep(3 * 1000);

					// After 5 seconds redirect to another intent
					db = new UsuarioDB(getApplicationContext());
					usuario = db.listaUsuario();
					if (usuario.getEmail() != null) {

						Intent homeIntent = new Intent(getApplicationContext(),
								HomeActivity.class);
						homeIntent.putExtra("usuario", usuario);
						homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(homeIntent);
						finish();
					}else{
						Intent homeLoginIntent = new Intent(
								getApplicationContext(), LoginActivity.class);
						homeLoginIntent
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
}
