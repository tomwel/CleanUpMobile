package br.com.clean_up_mobile.adapter;

import java.util.List;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Servico;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServicoAdapter extends ArrayAdapter<Servico> {

	public ServicoAdapter(Context context, List<Servico> objects) {
		super(context, 0, 0, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		Servico s = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_lista_servico, null);

			holder = new ViewHolder();
			holder.textDia = (TextView) convertView.findViewById(R.id.textDia);

			holder.textMes = (TextView) convertView.findViewById(R.id.textMes);

			holder.textEndereco = (TextView) convertView
					.findViewById(R.id.textEndereco);

			holder.imageStatus = (ImageView) convertView
					.findViewById(R.id.imageStatus);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textDia.setText("12");
		holder.textMes.setText("out");
		holder.textEndereco.setText(s.getEndereco());
		
		Log.v("FLS", s.getStatus());
		
		if (s.getStatus().equals("PENDENTE")) {
			holder.imageStatus.setImageResource(R.drawable.ic_status_servico_pendente);
		} else if (s.getStatus().equals("ATIVO")) {
			holder.imageStatus.setImageResource(R.drawable.ic_status_servico_ativo);
		} else if (s.getStatus().equals("INATIVO")) {
			holder.imageStatus.setImageResource(R.drawable.ic_status_servico_inativo);
		} else {
			holder.imageStatus.setImageResource(R.drawable.ic_status_servico_sem_imagem);
		}

		return convertView;
	}

	static class ViewHolder {
		TextView textDia;
		TextView textMes;
		TextView textEndereco;
		ImageView imageStatus;

	}
}