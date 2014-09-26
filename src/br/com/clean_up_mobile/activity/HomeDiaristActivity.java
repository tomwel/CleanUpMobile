package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.UsuarioDB;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeDiaristActivity extends Activity {
	ProgressDialog prgDialog;
	TextView diaristName, logout;
	Usuario usuario;
	UsuarioDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_diarist);
		db = new UsuarioDB(getApplicationContext());
		prgDialog = new ProgressDialog(this);
		prgDialog.setMessage("Please wait...");
		prgDialog.setCancelable(false);
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		diaristName = (TextView) findViewById(R.id.textViewNameDiarist);
		diaristName.setText(usuario.getApelido());

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
