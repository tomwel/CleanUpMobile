package br.com.clean_up_mobile.activity;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.UsuarioDB;
import br.com.clean_up_mobile.fragment.ServicoFragment;
import br.com.clean_up_mobile.model.Usuario;

import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeDiaristaActivity extends ActionBarActivity implements
		TabListener {
	Fragment fragment1;
	Fragment fragment2;
	ViewPager pager;
	Usuario usuario;
	UsuarioDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_diarista);
		
		db = new UsuarioDB(getApplicationContext());
		usuario = (Usuario) getIntent().getSerializableExtra("usuario");
		
		fragment1 = new ServicoFragment(true, getApplicationContext(),  "ATIVO");
		fragment2 = new ServicoFragment(false, getApplicationContext(), "TODOS");

		final ActionBar actionBar = getSupportActionBar();

		pager = (ViewPager) findViewById(R.id.viewPager);
		FragmentManager fm = getSupportFragmentManager();
		pager.setAdapter(new MeuAdapter(fm));
		pager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab aba1 = actionBar.newTab();
		aba1.setText("Próximos");
		aba1.setTabListener(this);

		Tab aba2 = actionBar.newTab();
		aba2.setText("Todos");
		aba2.setTabListener(this);

		actionBar.addTab(aba1);
		actionBar.addTab(aba2);
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
			Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
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
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

	}

	class MeuAdapter extends FragmentPagerAdapter {

		public MeuAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				return fragment1;
			}
			return fragment2;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}
}