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
		db = new UsuarioDB(getApplicationContext());
		usuario = db.listaUsuario();
		if ( usuario.getEmail() != null) {
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
