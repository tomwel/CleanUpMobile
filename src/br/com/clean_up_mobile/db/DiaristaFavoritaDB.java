package br.com.clean_up_mobile.db;

import java.util.ArrayList;
import java.util.List;

import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Especialidade;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DiaristaFavoritaDB {

	private DBHelper helper;
	private Context contexto;

	public DiaristaFavoritaDB(Context contexto) {
		this.contexto = contexto;
		helper = new DBHelper(contexto);
	}

	public long inserir(Diarista diarista) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = valoresPorDiarista(diarista);

		long id = db.insert("diarista_favorita", null, values);

		db.close();

		return id;
	}
	public ContentValues valoresPorDiarista(Diarista diarista) {
		ContentValues values = new ContentValues();
		values.put("codigo", diarista.getCodigo());
		values.put("nome", diarista.getNome());
		values.put("telefone", diarista.getTelefone());
		values.put("cidade", diarista.getCidade());
		diarista.favorito = true;

		return values;
	}
	
	public int excluir(Diarista diarista) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db.delete("diarista_favorita", "codigo = " + diarista.getCodigo(),
				null);
		db.close();

		return rows;
	}

	public boolean favorito(Diarista diarista) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from diarista_favorita where codigo="
				+ diarista.getCodigo(), null);

		boolean resultado = false;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			diarista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
			resultado = true;
		}
		cursor.close();
		db.close();
		return resultado;
	}

	public List<Diarista> listaDiaristas() {
		List<Diarista> diaristas = new ArrayList<Diarista>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from diarista_favorita", null);

		while (cursor.moveToNext()) {
			Diarista diarista = preencherDiarista(cursor);
			diaristas.add(diarista);
		}
		cursor.close();
		db.close();
		return diaristas;
	}

	private Diarista preencherDiarista(Cursor cursor) {

		Integer codigo = Integer.parseInt(cursor.getString(0));
		String nome = cursor.getString(1);
		String telefone = cursor.getString(2);
		String cidade = cursor.getString(3);

		EspecialidadeDB especialidadeDB = new EspecialidadeDB(contexto);

		List<Especialidade> especialidades = especialidadeDB
				.pegarEspecialidades(codigo);

		Diarista diarista = new Diarista(codigo, nome, telefone, cidade,
				especialidades);

		return diarista;
	}

}
