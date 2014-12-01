package br.com.clean_up_mobile.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.vo.ClienteVO;

public class ClienteVOHttp {

	public static ClienteVO retrieveCliente(Integer codUsuario)
			throws Exception, IOException {
		URL url = new URL(Constantes.GET_CLIENTELOGADO + "/" + codUsuario);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setReadTimeout(10000);
		conexao.setConnectTimeout(15000);
		conexao.setDoInput(true);
		conexao.connect();

		if (conexao.getResponseCode() == 200) { // HTTP_OK
			return carregarCliente(conexao.getInputStream());
		}
		return null;
	}

	public static ClienteVO carregarCliente(InputStream is)
			throws JSONException, IOException {
		JSONObject jsonCliente = new JSONObject(bytesToString(is));
		JSONObject jsonCidade = jsonCliente.getJSONObject("cidade");
		Cidade cidade = new Cidade();
		cidade.setCodigoCidade(jsonCidade.getInt("codigoCidade"));
		cidade.setNomeCidade(jsonCidade.getString("nomeCidade"));

		ClienteVO clienteVO = new ClienteVO();
		clienteVO.setCodigo(jsonCliente.getInt("codigo"));
		clienteVO.setFotoUsuario(jsonCliente.getString("fotoUsuario"));
		clienteVO.setNome(jsonCliente.getString("nome"));
		clienteVO.setCpf(jsonCliente.getString("cpf"));
		clienteVO.setTelefone(jsonCliente.getString("telefone"));
		clienteVO.setCidade(cidade);
		clienteVO.setSenha(jsonCliente.getString("senha"));
		clienteVO.setEmail(jsonCliente.getString("email"));

		return clienteVO;

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
