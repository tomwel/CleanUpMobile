package br.com.clean_up_mobile.adapter;

import java.util.List;
import java.util.regex.Pattern;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.R.drawable;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

public class DiaristasAdapter extends ArrayAdapter<Diarista> {

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
		for (int i = 0; i < especialidades.size(); i++) {
			String virgula = ",";

			if (especialidades.get(i) != null) {
				especialidade = especialidades.get(i);
				listaEspecialidades = listaEspecialidades
						+ especialidade.getNomeEspecialidade() + virgula + " ";
			} else {
				break;
			}
		}
		listaEspecialidades = listaEspecialidades.substring(0,
				listaEspecialidades.length() - 2);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_lista_diarista, null);

			holder = new ViewHolder();

			holder.nomeDiarista = (TextView) convertView
					.findViewById(R.id.textViewNomeListDiarista);

			holder.fotoDiarista = (ImageView) convertView
					.findViewById(R.id.imageViewDiaristaDetalhe);

			holder.cidadeDiarista = (TextView) convertView
					.findViewById(R.id.textViewLoading);

			holder.especialidadesDiarista = (TextView) convertView
					.findViewById(R.id.textViewEspecialidadesListDiarista);

			holder.clasificacaoDiarista = (RatingBar) convertView
					.findViewById(R.id.ratingBarClassificacaoDiarista);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.nomeDiarista.setText(diarista.getNome());
		if (!diarista.getFotoUsuario().equals(
				"/cleanUp/resources/assets/img/avatar.jpg")) {
			Bitmap bitmap;
			bitmap = decodeBase64(diarista.getFotoUsuario());
			bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
			holder.fotoDiarista.setImageBitmap(bitmap);
		}else{
			holder.fotoDiarista.setImageResource(R.drawable.diarista);
		}
		// holder.cidadeDiarista.setText(diarista.getCidade().getNomeCidade());
		holder.cidadeDiarista.setText(diarista.getCidade());
		holder.especialidadesDiarista.setText(listaEspecialidades);
		holder.clasificacaoDiarista.setRating(diarista.getMediaDiarista()
				.floatValue());

		return convertView;
	}

	private Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	static class ViewHolder {
		TextView codigoDiarista;
		TextView nomeDiarista;
		TextView cidadeDiarista;
		TextView especialidadesDiarista;
		SearchView mSearchView;
		RatingBar clasificacaoDiarista;
		ImageView fotoDiarista;
	}

	public static Bitmap decodeBase64(String input) {
		int inicio;
		int fim;
		inicio = input.indexOf(",");
		fim = input.length();
		String imgBase64 = input.substring(inicio + 1, fim);
		byte[] decodedByte = Base64.decode(imgBase64, Base64.DEFAULT);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
}
