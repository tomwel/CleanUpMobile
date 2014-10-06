package br.com.clean_up_mobile.fragment;

import br.com.clean_up_mobile.R;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ClienteFragment extends FragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_cliente);

		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

	}

}
