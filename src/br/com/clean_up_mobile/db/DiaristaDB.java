package br.com.clean_up_mobile.db;

import java.util.Iterator;
import java.util.List;

import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DiaristaDB {
	private DBHelper helper;
	private Context contexto;

	public DiaristaDB(Context contexto) {
		this.contexto = contexto;
		helper = new DBHelper(contexto);
	}

	public int excluir(Diarista diarista) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db.delete("diarista", "codigo = " + diarista.getCodigo(),
				null);
		db.close();

		return rows;
	}

	public int excluirTodos() {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db.delete("diarista", null, null);
		db.close();

		return rows;
	}

	public ContentValues valoresPorDiarista(Diarista diarista) {
		ContentValues values = new ContentValues();
		values.put("codigo", diarista.getCodigo());
		values.put("nome", diarista.getNome());
		values.put("telefone", diarista.getTelefone());
		return values;
	}

	public Diarista pegarDiarista(Integer codigoDaDiarista) {

		Diarista diarista = new Diarista();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from diarista where codigo="
				+ codigoDaDiarista, null);

		while (cursor.moveToNext()) {
			diarista = preencherDiarista(cursor);
		}
		cursor.close();
		db.close();

		return diarista;
	}

	private Diarista preencherDiarista(Cursor cursor) {

		Integer codigo = Integer.parseInt(cursor.getString(0));
		String nome = cursor.getString(1);
		String telefone = cursor.getString(2);

		EspecialidadeDB especialidadeDB = new EspecialidadeDB(contexto);

		List<Especialidade> especialidades = especialidadeDB
				.pegarEspecialidades(codigo);

		Diarista diarista = new Diarista(codigo, nome, telefone, null,
				especialidades);

		return diarista;
	}
}