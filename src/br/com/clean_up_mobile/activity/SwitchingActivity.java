package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.fragment.DetalheServicoFragment;
import br.com.clean_up_mobile.model.ServicoSimples;
import br.com.clean_up_mobile.model.Usuario;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class SwitchingActivity extends ActionBarActivity {
	Usuario usuario;
	UsuarioDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_diarista);

		db = new UsuarioDB(getApplicationContext());
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		
//		Intent i = getIntent();
//        Servico servico = (Servico) i.getSerializableExtra("servico");
//        
//        

        ServicoSimples servico = (ServicoSimples) getIntent().getSerializableExtra("servico");
        DetalheServicoFragment detalhe = DetalheServicoFragment.novaInstancia(servico);
		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, detalhe).commit();
//		
//		FragmentManager fragmentManager = getSupportFragmentManager();
//		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//		fragmentTransaction.replace(android.R.id.content, new DetalheServicoFragment());
//		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_diarista, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// action with ID action_refresh was selected
		case R.id.logout:
			try {
				db.excluir(usuario);
				navigatetoLoginActivity();
				Toast.makeText(this, "Até Logo!", Toast.LENGTH_SHORT).show();
				break;
			} catch (Exception e) {
				e.getMessage();
			}
		case R.id.action_settings:
			Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
					.show();
			break;
		}
		return true;
	}

	public void navigatetoLoginActivity() {
		Intent loginIntent = new Intent(getApplicationContext(),
				LoginActivity.class);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginIntent);
		finish();
	}
}