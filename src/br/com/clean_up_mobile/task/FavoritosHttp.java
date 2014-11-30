package br.com.clean_up_mobile.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Favorito;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.Constantes;

public class FavoritosHttp {
	public static List<Diarista> getFavoritos(Usuario usu) throws Exception, IOException {
		URL url = new URL(Constantes.GET_FAVORITOS);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setReadTimeout(10000);
		conexao.setConnectTimeout(15000);
		conexao.setDoInput(true);
		conexao.connect();

		if (conexao.getResponseCode() == 200) { // HTTP_OK
			return carregarDiaristas(conexao.getInputStream());
		}
		return null;

	}

	public static List<Diarista> carregarDiaristas(InputStream is)
			throws JSONException, IOException {
		JSONArray jsonDiaristas;
		jsonDiaristas = new JSONArray(bytesToString(is));
		List<Diarista> diaristas = new ArrayList<Diarista>(jsonDiaristas.length());
		for (int i = 0; i < jsonDiaristas.length(); i++) {
			Diarista diarista = new Diarista();
			Cidade cidade = new Cidade();
			List<Especialidade> especialidades = new ArrayList<Especialidade>();
			JSONObject jsonDiarista = jsonDiaristas.getJSONObject(i);
			diarista.setCodigo(jsonDiarista.getInt("codigo"));
			diarista.setNome(jsonDiarista.getString("nome"));
			diarista.setMediaDiarista(jsonDiarista.getDouble("mediaDiarista"));
			JSONObject jsonCidade = jsonDiarista.getJSONObject("cidade");
//			cidade.setCodigoCidade(jsonCidade.getInt("codigoCidade"));
//			cidade.setNomeCidade(jsonCidade.getString("nomeCidade"));
			diarista.setCidade(jsonCidade.getString("nomeCidade"));
			JSONArray jsonEspecialidades = jsonDiarista.getJSONArray("especialidades");
			for (int j = 0; j < jsonEspecialidades.length(); j++) {
				Especialidade especialidade = new Especialidade();
				JSONObject jsonEspecialidade = jsonEspecialidades.getJSONObject(j);
				especialidade.setCodigoEspecialidade(jsonEspecialidade.getInt("codigoEspecialidade"));
				especialidade.setNomeEspecialidade(jsonEspecialidade.getString("nomeEspecialidade"));
				especialidades.add(especialidade);
				diarista.setEspecialidades(especialidades);
			}
			
			
			diaristas.add(diarista);
		}

		return diaristas;

	}

	private static String bytesToString(InputStream is) throws IOException {
		byte[] bufferzinho = new byte[1024];
		ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
		int bytesLidos;
		while ((bytesLidos = is.read(bufferzinho)) != -1) {
			bufferzao.write(bufferzinho, 0, bytesLidos);
		}
		return new String(bufferzao.toByteArray());
	}
}
