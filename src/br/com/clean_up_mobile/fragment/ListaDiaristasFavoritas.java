package br.com.clean_up_mobile.fragment;

import java.util.List;

import br.com.clean_up_mobile.activity.OnClickDiarista;
import br.com.clean_up_mobile.adapter.DiaristasAdapter;
import br.com.clean_up_mobile.db.DiaristaDB;
import br.com.clean_up_mobile.db.DiaristaFavoritaDB;
import br.com.clean_up_mobile.model.Diarista;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class ListaDiaristasFavoritas extends ListFragment {
	List<Diarista> mDiaristas;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		refreshList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		if (getActivity() instanceof OnClickDiarista){
			((OnClickDiarista)getActivity()).clickedDiarista(mDiaristas.get(position));
		}
	}

	protected void refreshList() {
		DiaristaFavoritaDB db = new DiaristaFavoritaDB(getActivity());
		mDiaristas = db.listaDiaristas();
		
		DiaristasAdapter adapter = new DiaristasAdapter(getActivity(), mDiaristas);
		setListAdapter(adapter);
	}
}
