package br.com.clean_up_mobile.db;

import br.com.clean_up_mobile.model.Cliente;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ClienteDB {
	private DBHelper helper;

	public ClienteDB(Context contexto) {
		helper = new DBHelper(contexto);
	}

	public long inserir(Cliente cliente) {
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = valoresPorCliente(cliente);

		long id = db.insert("usuario", null, values);
		cliente.setCodigo((int) id);

		db.close();

		return id;
	}

	public int excluir(Cliente cliente) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db
				.delete("cliente", "codigo = " + cliente.getCodigo(), null);
		db.close();

		return rows;
	}

	private ContentValues valoresPorCliente(Cliente cliente) {
		ContentValues values = new ContentValues();
		values.put("codigo", cliente.getCodigo());
		values.put("nome", cliente.getNome());
		values.put("telefone", cliente.getTelefone());
		return values;
	}

	public Cliente pegarCliente(Integer idDoCliente) {

		Cliente cliente = new Cliente();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from usuario where codigo="
				+ idDoCliente, null);

		while (cursor.moveToNext()) {
			cliente = preencherCliente(cursor);
		}
		cursor.close();
		db.close();
		return cliente;
	}

	private Cliente preencherCliente(Cursor cursor) {

		Integer codigo = Integer.parseInt(cursor.getString(0));
		String nome = cursor.getString(1);
		String telefone = cursor.getString(2);

		Cliente cliente = new Cliente(codigo, nome, telefone);

		return cliente;
	}
}