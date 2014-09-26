package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.UsuarioDB;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeClientActivity extends Activity {
	TextView clientName, logout;
	Usuario usuario;
	UsuarioDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_client);
		db = new UsuarioDB(getApplicationContext());
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		clientName = (TextView) findViewById(R.id.textViewNameClient);
		clientName.setText(usuario.getApelido());

		logout = (TextView) findViewById(R.id.textViewLogout);

		logout.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (logout.getLinksClickable() == true) {
					logout.setLinkTextColor(Color.BLUE);
				}
				try {
					db.excluir(usuario);
					navigatetoLoginActivity();
				} catch (Exception e) {
					e.getMessage();
				}
			}

		});
	}

	public void navigatetoLoginActivity() {
		Intent loginIntent = new Intent(getApplicationContext(),
				LoginActivity.class);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginIntent);
		finish();
	}
}
