package br.com.clean_up_mobile.adapter;

import java.util.List;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Diarista;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiaristasAdapter extends ArrayAdapter<Diarista>{
	
	public DiaristasAdapter(Context context, List<Diarista> objects) {
		super(context, 0, 0, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		Diarista diarista = getItem(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_lista_diarista, null);
			holder = new ViewHolder();
			holder.nomeDiarista = (TextView) convertView
					.findViewById(R.id.textViewNomeListDiarista);

			holder.valorDiarista = (TextView) convertView
					.findViewById(R.id.textViewValorListDiarista);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		System.out.println(diarista.getNome());
		holder.nomeDiarista.setText(diarista.getNome());
		holder.valorDiarista.setText(Double.toString(diarista.getValor()));
		
		return convertView;
	}

	static class ViewHolder {
		TextView nomeDiarista;
		TextView valorDiarista;
	}
}
