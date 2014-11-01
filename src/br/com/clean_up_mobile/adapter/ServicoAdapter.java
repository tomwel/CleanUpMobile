package br.com.clean_up_mobile.adapter;

import java.util.List;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Servico;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ServicoAdapter extends ArrayAdapter<Servico> {

	public ServicoAdapter(Context context, List<Servico> objects) {
		super(context, 0, 0, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		Servico servico = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_lista_servico, null);

			holder = new ViewHolder();
			holder.dia = (TextView) convertView
					.findViewById(R.id.textDia);

			holder.mes = (TextView) convertView
					.findViewById(R.id.textMes);

			holder.endereco = (TextView) convertView
					.findViewById(R.id.textEndereco);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.dia.setText(12);
		holder.mes.setText("out");
		holder.endereco.setText("Casa de cassete");

		return convertView;
	}

	static class ViewHolder {
		TextView dia;
		TextView mes;
		TextView endereco;
	}
}
