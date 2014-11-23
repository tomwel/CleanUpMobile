package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.fragment.DetalheDiaristaFragment;
import br.com.clean_up_mobile.model.Diarista;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DetalheDiaristaActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Diarista diarista = (Diarista) getIntent().getSerializableExtra("diarista");
		DetalheDiaristaFragment detalhe = DetalheDiaristaFragment.novaInstancia(diarista);

		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, detalhe).commit();
	}

}
