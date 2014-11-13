package br.com.clean_up_mobile.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.clean_up_mobile.db.ClienteDB;
import br.com.clean_up_mobile.db.DiaristaDB;
import br.com.clean_up_mobile.db.EspecialidadeDB;
import br.com.clean_up_mobile.db.ServicoDB;
import br.com.clean_up_mobile.model.Cliente;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Servico;
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

		EspecialidadeDB especialidadeDB = new EspecialidadeDB(context);
		ClienteDB clienteDB = new ClienteDB(context);
		DiaristaDB diaristaDB = new DiaristaDB(context);

		especialidadeDB.excluirTodosRelacionamento();
		clienteDB.excluirTodos();
		diaristaDB.excluirTodos();
		servicoDB.excluirTodos();

	}

	public void inserirServicos(List<Servico> listaServicos) {
		limpaTodosServicos();
		servicoDB.inserir(listaServicos);
	}

	public List<Servico> listarServicosLocal() {
		List<Servico> list = new ArrayList<Servico>();

		list = servicoDB.listarServico();
		return list;
	}

	public List<Servico> pegarListaServicosLocal() {

		List<Servico> listaServicos = new ArrayList<Servico>();
		listaServicos = listarServicosLocal();

		return listaServicos;
	}

	public boolean atualizarBancoLocal(String tipo, int codigo) {

		String tipoBusca;
		List<Servico> listaServicos = new ArrayList<Servico>();

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

	public List<Servico> montaListServico(String result) throws JSONException {

		List<Servico> listaServicos = new ArrayList<Servico>();
		JSONArray servicos = new JSONArray(result);

		for (int i = 0; i < servicos.length(); i++) {

			Diarista diarista = new Diarista();
			List<Especialidade> especialidades = new ArrayList<Especialidade>();
			Cliente cliente = new Cliente();
			Servico servico = new Servico();

			JSONObject s = servicos.getJSONObject(i);

			// diarista
			JSONObject d = s.getJSONObject("diarista");
			JSONArray es = d.getJSONArray("especialidades");
			JSONObject ed = s.getJSONObject("endereco");

			// especialidades
			for (int j = 0; j < es.length(); j++) {
				Especialidade especialidade = new Especialidade();

				JSONObject e = es.getJSONObject(j);
				especialidade.setCodigoEspecialidade(e
						.getInt("codigoEspecialidade"));
				especialidade.setNomeEspecialidade(e
						.getString("nomeEspecialidade"));
				especialidades.add(especialidade);
			}

			diarista.setCidade("null");
			diarista.setCodigo(d.getInt("codigo"));
			diarista.setEspecialidades(especialidades);
			diarista.setNome(d.getString("nome"));
			diarista.setTelefone(d.getString("telefone"));

			// cliente
			JSONObject c = s.getJSONObject("cliente");
			cliente.setCodigo(c.getInt("codigo"));
			cliente.setNome(c.getString("nome"));
			cliente.setTelefone(c.getString("telefone"));

			// servico

			servico.setCodServico(s.getInt("codServico"));
			servico.setCliente(cliente);
			servico.setDiarista(diarista);
			servico.setDataServico(new Date(s.getInt("dataServico")));
			servico.setEndereco(ed.getString("logradouro"));
			servico.setStatus(s.getString("status"));
			servico.setDescricao(s.getString("descricao"));

			listaServicos.add(servico);
		}

		return listaServicos;

	}
}