package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.fragment.ClienteFragment;
import br.com.clean_up_mobile.fragment.DiaristaFragment;
import br.com.clean_up_mobile.fragment.TopoFragment;
import br.com.clean_up_mobile.model.Usuario;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {

	TextView clientName, logout;
	Usuario usuario;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		showFragmentTopo();
		if (usuario.getPerfil().equals("ROLE_CLIENT")) {
			showFragmentCliente();
		} else if (usuario.getPerfil().equals("ROLE_DIARIST")) {
			showFragmentDiarista();
		}

	}

	public void showFragmentTopo() {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.frameTopo, new TopoFragment());
		transaction.commit();
	}

	public void showFragmentCliente() {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.frameCentro, new ClienteFragment());
		transaction.commit();
	}

	public void showFragmentDiarista() {

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.frameCentro, new DiaristaFragment());
		transaction.commit();
	}
}