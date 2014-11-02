package br.com.clean_up_mobile.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.clean_up_mobile.model.Especialidade;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EspecialidadeDB {

	private DBHelper helper;

	public EspecialidadeDB(Context contexto) {
		helper = new DBHelper(contexto);
	}

	public ContentValues valoresPorEspecialidade(Especialidade especialidade) {
		ContentValues values = new ContentValues();
		values.put("codigo", especialidade.getCodigoEspecialidade());
		values.put("nome", especialidade.getNomeEspecialidade());
		return values;
	}

	public boolean existeEspecialidade(Integer idDoEsepcialidade) {

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from especialidade where codigo="
				+ idDoEsepcialidade, null);

		boolean resultado = false;
		if (cursor != null && cursor.getCount() > 0) {
			resultado = true;
		}
		cursor.close();
		db.close();
		return resultado;

	}

	public List<Especialidade> pegarEspecialidades(Integer idDiarista) {

		List<Especialidade> listaEspecialidade = new ArrayList<Especialidade>();

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db
				.rawQuery(
						"select codigo, nome from especialidade "
								+ "inner join relacionamento_especialidade_diarista"
								+ " on especialidade.codigo = relacionamento_especialidade_diarista.especialidade "
								+ "where relacionamento_especialidade_diarista.diarista = "
								+ idDiarista, null);

		while (cursor.moveToNext()) {
			listaEspecialidade.add(preencherEspecialidade(cursor));
		}

		cursor.close();
		db.close();

		return listaEspecialidade;
	}

	private Especialidade preencherEspecialidade(Cursor cursor) {

		Integer codigo = Integer.parseInt(cursor.getString(0));
		String nome = cursor.getString(1);

		Especialidade especialidade = new Especialidade(codigo, nome);

		return especialidade;
	}

	public int excluirTodasEspecialidades() {

		SQLiteDatabase db = helper.getWritableDatabase();
		int rows = db.delete("especialidade", null, null);
		db.close();

		return rows;
	}

	// relacionamento diarista X especialidade

	public int excluirRelacionamento(Integer idDiarista) {

		SQLiteDatabase db = helper.getWritableDatabase();
		int rows = db.delete("relacionamento_especialidade_diarista",
				"diarista = " + idDiarista, null);
		db.close();

		return rows;
	}

	public int excluirTodosRelacionamento() {

		SQLiteDatabase db = helper.getWritableDatabase();
		int rows = db.delete("relacionamento_especialidade_diarista", null,
				null);
		db.close();

		return rows;
	}

	public ContentValues valoresPorRelacionamento(Integer codigoDiarista,
			Integer codigoEspecialidade) {
		ContentValues values = new ContentValues();
		values.put("diarista", codigoDiarista);
		values.put("especialidade", codigoEspecialidade);
		return values;
	}
}