package br.com.clean_up_mobile.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.clean_up_mobile.model.Cliente;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Servico;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ServicoDB {
	private DBHelper helper;
	private Context contexto;

	public ServicoDB(Context contexto) {
		this.contexto = contexto;
		helper = new DBHelper(contexto);
	}

	public void inserir(List<Servico> listaServicos) {

		List<Especialidade> especialidades = new ArrayList<Especialidade>();

		ClienteDB clienteDB = new ClienteDB(contexto);
		DiaristaDB diaristaDB = new DiaristaDB(contexto);
		EspecialidadeDB especialidadeDB = new EspecialidadeDB(contexto);

		SQLiteDatabase db = helper.getReadableDatabase();
		db.beginTransaction();

		for (Iterator<Servico> it = listaServicos.iterator(); it.hasNext();) {
			Servico servico = it.next();
			Cliente cliente = servico.getCliente();
			Diarista diarista = servico.getDiarista();

			ContentValues valuesServico = valoresPorServico(servico);
			ContentValues valuesCliente = clienteDB.valoresPorCliente(cliente);
			ContentValues valuesDiarista = diaristaDB.valoresPorDiarista(diarista);

			db.insert("servico", null, valuesServico);
			db.insert("cliente", null, valuesCliente);
			db.insert("diarista", null, valuesDiarista);

			especialidades.clear();
			especialidades = diarista.getEspecialidades();

			for (Iterator<Especialidade> es = especialidades.iterator(); es
					.hasNext();) {

				Especialidade especialidade = es.next();

				ContentValues valuesEspecialidade = especialidadeDB
						.valoresPorEspecialidade(especialidade);

				db.insert("especialidade", null, valuesEspecialidade);

				// insert relacionamentos
				ContentValues valuesRelacionamento = especialidadeDB
						.valoresPorRelacionamento(diarista.getCodigo(), 
								especialidade.getCodigoEspecialidade());

				db.insert("relacionamento_especialidade_diarista", null,
						valuesRelacionamento);
			}
		}

		db.setTransactionSuccessful();
		db.endTransaction();
	}

	private ContentValues valoresPorServico(Servico servico) {
		ContentValues values = new ContentValues();
		values.put("codigo", servico.getCodServico());
		values.put("tipo", servico.getTipoServico());
		values.put("descricao", servico.getDescricao());
		values.put("cliente", servico.getCliente().getCodigo());
		values.put("diarista", servico.getDiarista().getCodigo());
		values.put("endereco", "");
		values.put("data", servico.getDataServico().toString());
		values.put("valor", servico.getValor());
		values.put("status", servico.getStatus());
		return values;
	}

	public int excluir(Servico servico) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db
				.delete("servico", "id = " + servico.getCodServico(), null);
		db.close();

		return rows;
	}

	public int excluirTodos() {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db.delete("servico", null, null);
		db.close();

		return rows;
	}

	public Servico pegarServico(Integer codigoDoServico) throws ParseException {

		Servico servico = new Servico();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from servico where codigo="
				+ codigoDoServico, null);

		while (cursor.moveToNext()) {
			servico = preencherServico(cursor);
		}
		cursor.close();
		db.close();

		return servico;
	}

	public List<Servico> listarServico() throws ParseException {

		List<Servico> listaServico = new ArrayList<Servico>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from servico where 1=1", null);

		while (cursor.moveToNext()) {
			listaServico.add(preencherServico(cursor));
		}

		cursor.close();
		db.close();
		return listaServico;
	}

	private Servico preencherServico(Cursor cursor) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		ClienteDB clienteDB = new ClienteDB(contexto);
		DiaristaDB diaristaDB = new DiaristaDB(contexto);

		Integer codigo = cursor.getInt(0);
		String tipo = cursor.getString(1);
		String descricao = cursor.getString(2);
		Integer codigoDoCliente = cursor.getInt(3);
		Integer codigoDaDiarista = cursor.getInt(4);
		String endereco = cursor.getString(5);
		Date data = new Date();
		double valor = Double.parseDouble(cursor.getString(7));
		String status = cursor.getString(8);

		Cliente cliente = clienteDB.pegarCliente(codigoDoCliente);
		Diarista diarista = diaristaDB.pegarDiarista(codigoDaDiarista);

		Servico servico = new Servico(codigo, tipo, descricao, cliente,
				diarista, null, data, valor, status);

		return servico;
	}
}