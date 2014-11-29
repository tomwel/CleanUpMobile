package br.com.clean_up_mobile.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.clean_up_mobile.db.ServicoDB;
import br.com.clean_up_mobile.model.ServicoSimples;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import android.content.Context;
import android.util.Log;

public class ServicoController {

	Context context;
	ServicoDB servicoDB;

	public ServicoController(Context c) {
		context = c;
		servicoDB = new ServicoDB(context);
	}

	public void limpaTodosServicos() {
		servicoDB.excluirTodos();
	}

	public void inserirServicos(List<ServicoSimples> listaServicos) {
		limpaTodosServicos();
		servicoDB.inserir(listaServicos);
	}

	public List<ServicoSimples> listarServicosLocal(String where) {
		List<ServicoSimples> list = new ArrayList<ServicoSimples>();

		list = servicoDB.listarServico(where);
		return list;
	}

	public List<ServicoSimples> pegarListaServicosLocal(String where) {
		

		List<ServicoSimples> listaServicos = new ArrayList<ServicoSimples>();
		listaServicos = listarServicosLocal(where);

		return listaServicos;
	}

	public boolean atualizarBancoLocal(String tipo, int codigo) {

		String tipoBusca;
		List<ServicoSimples> listaServicos = new ArrayList<ServicoSimples>();

		try {

			if (tipo.equals("ROLE_CLIENT")) {
				tipoBusca = "cliente";
			} else {
				tipoBusca = "diarista";
			}

			String result = WebService.getREST(Constantes.GET_SERVICO + "/"
					+ tipoBusca + "/" + codigo);

			Log.v("FLS", result);

			if (result != null) {
				listaServicos = montaListServico(result);
				inserirServicos(listaServicos);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public List<ServicoSimples> montaListServico(String result) throws JSONException {

		List<ServicoSimples> listaServicos = new ArrayList<ServicoSimples>();
		JSONArray servicos = new JSONArray(result);

		for (int i = 0; i < servicos.length(); i++) {
			ServicoSimples servico = new ServicoSimples();

			JSONObject s = servicos.getJSONObject(i);

			// diarista
			JSONObject diarista = s.getJSONObject("diarista");
			
			// cliente
			JSONObject cliente = s.getJSONObject("cliente");
			
			//endereço
			JSONObject ed = s.getJSONObject("endereco");

			// servico
			servico.setCodServico(s.getInt("codServico"));
			servico.setCliente(cliente.toString());
			servico.setDiarista(diarista.toString());
			servico.setDataServico(Long.parseLong(s.getString("dataServico")));
			servico.setEndereco(ed.toString());
			servico.setStatus(s.getString("status"));
			servico.setDescricao(s.getString("descricao"));
			servico.setAvaliacao(s.getInt("avaliacao"));
			servico.setComentario(s.getString("comentario"));

			listaServicos.add(servico);
		}
		return listaServicos;
	}
}