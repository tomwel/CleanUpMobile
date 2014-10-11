package br.com.clean_up_mobile.util;

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

public class DiaristasHttp {

	public static List<Diarista> retrieveDiaristas() throws Exception {
		URL url = new URL(Constantes.GET_DIARISTAS);
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
		List<Diarista> diaristas = new ArrayList<Diarista>();
		JSONArray jsonDiaristas;
		List<Especialidade> especialidades = new ArrayList<Especialidade>();
		jsonDiaristas = new JSONArray(bytesToString(is));
		for (int i = 0; i < jsonDiaristas.length(); i++) {
			Diarista diarista = new Diarista();
			Cidade cidade = new Cidade();
			JSONObject jsonDiarista = jsonDiaristas.getJSONObject(i);
			diarista.setCodigo(Integer.getInteger(jsonDiarista
					.getString("codigo")));
			diarista.setNome(jsonDiarista.getString("nome"));
			JSONObject jsonCidade = jsonDiarista.getJSONObject("cidade");
			cidade.setCodigoCidade(jsonCidade.getInt("codigoCidade"));
			cidade.setNomeCidade(jsonCidade.getString("nomeCidade"));
			diarista.setCidade(cidade);
			JSONArray jsonEspecialidades = jsonDiarista.getJSONArray("especialidades");
			for (int j = 0; j < jsonEspecialidades.length(); j++) {
				Especialidade especialidade = new Especialidade();
				JSONObject jsonEspecialidade = jsonEspecialidades.getJSONObject(j);
				especialidade.setCodigoEspecialidade(Integer.getInteger(jsonEspecialidade.getString("codigoEspecialidade")));
				especialidade.setNomeEspecialidade(jsonEspecialidade.getString("nomeEspecialidade"));
				especialidades.add(especialidade);
				diarista.setEspecialidade(especialidades);
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
