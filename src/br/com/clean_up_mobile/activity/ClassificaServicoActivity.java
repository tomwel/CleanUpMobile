package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.fragment.ClassificaServicoFragment;
import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.model.ServicoSimples;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ClassificaServicoActivity extends ActionBarActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ServicoSimples servico = (ServicoSimples) getIntent().getSerializableExtra("servico");
		ClassificaServicoFragment classificaServico = ClassificaServicoFragment.novaInstancia(servico);

		getSupportFragmentManager().beginTransaction()
				.replace(android.R.id.content, classificaServico).commit();
	}
}
