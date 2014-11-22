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
import br.com.clean_up_mobile.util.Constantes;

public class CidadesHttp {

	public static List<Cidade> retrieveCidades() throws Exception, IOException {
		URL url = new URL(Constantes.GET_CIDADES);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setReadTimeout(10000);
		conexao.setConnectTimeout(15000);
		conexao.setDoInput(true);
		conexao.connect();

		if (conexao.getResponseCode() == 200) { // HTTP_OK
			return carregarCidades(conexao.getInputStream());
		}
		return null;

	}

	public static List<Cidade> carregarCidades(InputStream is)
			throws JSONException, IOException {
		JSONArray jsonCidades;
		jsonCidades = new JSONArray(bytesToString(is));
		List<Cidade> cidades = new ArrayList<Cidade>(jsonCidades.length());
		for (int i = 0; i < jsonCidades.length(); i++) {
			Cidade cidade = new Cidade();
			JSONObject jsonCidade = jsonCidades.getJSONObject(i);
			cidade.setCodigoCidade(jsonCidade.getInt("codigoCidade"));
			cidade.setNomeCidade(jsonCidade.getString("nomeCidade"));

			cidades.add(cidade);
		}

		return cidades;

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
