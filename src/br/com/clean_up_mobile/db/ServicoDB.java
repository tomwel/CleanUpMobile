package br.com.clean_up_mobile.db;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.clean_up_mobile.model.Servico;
import br.com.clean_up_mobile.model.ServicoSimples;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ServicoDB {
	private DBHelper helper;
	private Context contexto;

	public ServicoDB(Context contexto) {
		this.contexto = contexto;
		helper = new DBHelper(contexto);
	}

	public void inserir(List<ServicoSimples> listaServicos) {

		SQLiteDatabase db = helper.getReadableDatabase();
		db.beginTransaction();

		for (Iterator<ServicoSimples> it = listaServicos.iterator(); it
				.hasNext();) {
			ServicoSimples servico = it.next();

			ContentValues valuesServico = valoresPorServico(servico);
			db.insert("servico", null, valuesServico);
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	private ContentValues valoresPorServico(ServicoSimples servico) {
		ContentValues values = new ContentValues();
		values.put("codigo", servico.getCodServico());
		values.put("tipo", servico.getTipoServico());
		values.put("descricao", servico.getDescricao());
		values.put("cliente", servico.getCliente());
		values.put("diarista", servico.getDiarista());
		values.put("endereco", servico.getEndereco());
		values.put("data", servico.getDataServico());
		values.put("valor", servico.getValor());
		values.put("status", servico.getStatus());
		values.put("avaliacao", servico.getAvaliacao());
		values.put("comentario", servico.getComentario());
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

	public ServicoSimples pegarServico(Integer codigoDoServico)
			throws ParseException {

		ServicoSimples servico = new ServicoSimples();
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

	public List<ServicoSimples> listarServico(String statusServico) {

		String causaWhere = "";
		List<ServicoSimples> listaServico = new ArrayList<ServicoSimples>();

		SQLiteDatabase db = helper.getReadableDatabase();

		if (statusServico.equals("ACEITO"))
			causaWhere = " AND status='" + statusServico + "'";

		Cursor cursor = db.rawQuery("select * from servico where 1=1"
				+ causaWhere, null);

		while (cursor.moveToNext()) {
			listaServico.add(preencherServico(cursor));
		}

		cursor.close();
		db.close();
		return listaServico;
	}

	private ServicoSimples preencherServico(Cursor cursor) {

		Integer codigo = cursor.getInt(0);
		String tipo = "";
		String descricao = cursor.getString(2);
		String cliente = cursor.getString(3);
		String diarista = cursor.getString(4);
		String endereco = cursor.getString(5);
		long data = Long.parseLong(cursor.getString(6));
		double valor = 8.8;
		String status = cursor.getString(8);
		int avaliacao = cursor.getInt(9);
		String comentario = cursor.getString(10);

		ServicoSimples servico = new ServicoSimples(codigo, tipo, descricao,
				cliente, diarista, endereco, data, valor, status, avaliacao, comentario);

		return servico;
	}
}