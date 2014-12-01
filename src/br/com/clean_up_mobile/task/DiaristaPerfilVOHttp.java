package br.com.clean_up_mobile.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.vo.DiaristaPerfilVO;

public class DiaristaPerfilVOHttp {

	public static DiaristaPerfilVO retrieveDiarista(Integer codUsuario)
			throws Exception, IOException {
		URL url = new URL(Constantes.GET_DIARISTALOGADA + "/" + codUsuario);
		HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
		conexao.setRequestMethod("GET");
		conexao.setReadTimeout(10000);
		conexao.setConnectTimeout(15000);
		conexao.setDoInput(true);
		conexao.connect();

		if (conexao.getResponseCode() == 200) { // HTTP_OK
			return carregarDiarista(conexao.getInputStream());
		}
			return null;
		}

	public static DiaristaPerfilVO carregarDiarista(InputStream is)
			throws JSONException, IOException {
		JSONObject jsonDiarista = new JSONObject(bytesToString(is));
		JSONObject jsonUsuario = jsonDiarista.getJSONObject("usuario");
		JSONObject jsonCidade = jsonDiarista.getJSONObject("cidade");
		JSONObject jsonEndereco = jsonDiarista.getJSONObject("endereco");
		JSONArray jsonEspecialidades = jsonDiarista
				.getJSONArray("especialidades");

		Usuario usuario = new Usuario();
		usuario.setId(jsonUsuario.getInt("id"));
		usuario.setApelido(jsonUsuario.getString("apelido"));
		usuario.setEmail(jsonUsuario.getString("email"));
		usuario.setSenha(jsonUsuario.getString("senha"));
		usuario.setPerfil(jsonUsuario.getString("perfil"));
		usuario.setAtivo(jsonUsuario.getBoolean("ativo"));

		Cidade cidade = new Cidade();
		cidade.setCodigoCidade(jsonCidade.getInt("codigoCidade"));
		cidade.setNomeCidade(jsonCidade.getString("nomeCidade"));

		Endereco endereco = new Endereco();
		endereco.setCodigo(jsonEndereco.getInt("codigo"));
		BigDecimal bigDecimalLng = new BigDecimal(jsonEndereco.getString("lng"));
		BigDecimal bigDecimalLat = new BigDecimal(jsonEndereco.getString("lat"));
		endereco.setLat(bigDecimalLat);
		endereco.setLng(bigDecimalLng);
		endereco.setLogradouro(jsonEndereco.getString("logradouro"));

		List<Especialidade> especialidades = new ArrayList<Especialidade>();
		ArrayList<Integer> especialidadesInt = new ArrayList<Integer>();
		if (jsonEspecialidades.length() > 0) {
			for (int j = 0; j < jsonEspecialidades.length(); j++) {
				Especialidade especialidade = new Especialidade();
				JSONObject jsonEspecialidade = jsonEspecialidades
						.getJSONObject(j);
				especialidade.setCodigoEspecialidade(jsonEspecialidade
						.getInt("codigoEspecialidade"));
				especialidade.setNomeEspecialidade(jsonEspecialidade
						.getString("nomeEspecialidade"));
				especialidades.add(especialidade);
				especialidadesInt.add(especialidade.getCodigoEspecialidade());
			}
		}

		DiaristaPerfilVO diaristaVO = new DiaristaPerfilVO();
		diaristaVO.setCodigo(jsonDiarista.getInt("codigo"));
		diaristaVO.setFotoUsuario(jsonDiarista.getString("fotoUsuario"));
		diaristaVO.setNome(jsonDiarista.getString("nome"));
		diaristaVO.setCpf(jsonDiarista.getString("cpf"));
		diaristaVO.setTelefone(jsonDiarista.getString("telefone"));
		diaristaVO.setCidade(cidade);
		diaristaVO.setUsuario(usuario);
		diaristaVO.setEndereco(endereco);
		diaristaVO.setEspecialidades(especialidades);
		diaristaVO.setNewEspecialidade(especialidadesInt);

		return diaristaVO;

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
