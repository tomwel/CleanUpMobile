package br.com.clean_up_mobile.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.db.CidadeDB;
import br.com.clean_up_mobile.db.EspecialidadeDB;
import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;

public class CadastroController {

	Context context;
	CidadeDB cidadeDB;

	public CadastroController(Context c) {
		context = c;
		cidadeDB = new CidadeDB(context);
	}

	public boolean cadastrar(String url, Object o) {

		int mensagemErrro;
		String result;

		try {
			result = WebService.getREST(url, o);

			if (result != null) {

				JSONObject obj = new JSONObject(result);

				// cadastro realizado
				if (obj.getBoolean("status")) {
					return true;
				} else {

					// cpf já cadastrado
					if (obj.getString("error_msg").equals("cpf_cadastrado")) {
						mensagemErrro = R.string.msgCpfCadastrado;
						// email já cadastrado
					} else if (obj.getString("error_msg").equals(
							"email_cadastrado")) {
						mensagemErrro = R.string.msgEmailCadastrado;
						// erro no servidor ao cadastrar
					} else {
						mensagemErrro = R.string.msgDeErroWebservice;
					}
					Util.criarToast(context, mensagemErrro);
				}
			} else {
				Util.criarToast(context, R.string.msgDeErroWebservice);
			}
		} catch (JSONException e) {
			Util.criarToast(context, R.string.msgDeErroWebservice);
		}
		return false;
	}

	public boolean atualizarEspecialidadesECidades() {

		try {
			atualizarEspecialidades();
			atualizarCidades();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void atualizarEspecialidades() throws JSONException {

		EspecialidadeDB especialidadeDB = new EspecialidadeDB(context);
		especialidadeDB.excluirTodasEspecialidades();

		String result = WebService.getREST(Constantes.GET_ESPECIALIDADES);

		List<Especialidade> listaEspecialidades = new ArrayList<Especialidade>();
		JSONArray especialidades = new JSONArray(result);

		for (int i = 0; i < especialidades.length(); i++) {

			JSONObject e = especialidades.getJSONObject(i);

			Especialidade especialidade = new Especialidade();

			especialidade.setCodigoEspecialidade(e
					.getInt("codigoEspecialidade"));
			especialidade
					.setNomeEspecialidade(e.getString("nomeEspecialidade"));

			listaEspecialidades.add(especialidade);
		}

		especialidadeDB.inserirEspecialidades(listaEspecialidades);
	}

	public void atualizarCidades() throws JSONException {

		cidadeDB.excluirTodasCidades();

		String result = WebService.getREST(Constantes.GET_CIDADES);

		List<Cidade> listaCidades = new ArrayList<Cidade>();
		JSONArray cidades = new JSONArray(result);

		for (int i = 0; i < cidades.length(); i++) {

			JSONObject c = cidades.getJSONObject(i);

			Cidade cidade = new Cidade();

			cidade.setCodigoCidade(c.getInt("codigoCidade"));
			cidade.setNomeCidade(c.getString("nomeCidade"));

			listaCidades.add(cidade);
		}
		cidadeDB.inserirCidades(listaCidades);
	}

	public List<Cidade> listarCidades() {
		return cidadeDB.listarCidades();
	}
}
