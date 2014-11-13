package br.com.clean_up_mobile.adapter;

import java.util.List;
import java.util.regex.Pattern;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;

public class DiaristasAdapter extends ArrayAdapter<Diarista>{
	
	public DiaristasAdapter(Context context, List<Diarista> objects) {
		super(context, 0, 0, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		Diarista diarista = getItem(position);
		List<Especialidade> especialidades = diarista.getEspecialidades();
		Especialidade especialidade = new Especialidade();
		String listaEspecialidades = "";
		for (int i=0;i<especialidades.size();i++){
			String virgula = ",";
				
			if (especialidades.get(i) != null){
				especialidade = especialidades.get(i);
				listaEspecialidades = listaEspecialidades + especialidade.getNomeEspecialidade() + virgula;
			}else{	
				break;
			}
		}
		listaEspecialidades = listaEspecialidades.substring(0, listaEspecialidades.length() - 1);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_lista_diarista, null);
			
			holder = new ViewHolder();
			
			holder.nomeDiarista = (TextView) convertView
					.findViewById(R.id.textViewNomeListDiarista);

			holder.cidadeDiarista = (TextView) convertView
					.findViewById(R.id.textViewLoading);
			
			holder.especialidadesDiarista = (TextView) convertView
					.findViewById(R.id.textViewEspecialidadesListDiarista);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.nomeDiarista.setText(diarista.getNome());
//		holder.cidadeDiarista.setText(diarista.getCidade().getNomeCidade());
		holder.cidadeDiarista.setText(diarista.getCidade());
		holder.especialidadesDiarista.setText(listaEspecialidades);
		
		return convertView;
	}

	static class ViewHolder {
		TextView codigoDiarista;
		TextView nomeDiarista;
		TextView cidadeDiarista;
		TextView especialidadesDiarista;
		SearchView mSearchView;
	}
}
