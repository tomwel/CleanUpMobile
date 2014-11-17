package br.com.clean_up_mobile.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.util.Constantes;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CidadeDB {

	private DBHelper helper;

	public CidadeDB(Context contexto) {
		helper = new DBHelper(contexto);
	}

	public ContentValues valoresPorCidade(Cidade cidade) {
		ContentValues values = new ContentValues();
		values.put("codigo", cidade.getCodigoCidade());
		values.put("nome", cidade.getNomeCidade());
		return values;
	}

	public void inserirCidades(List<Cidade> listaCidades) {

		SQLiteDatabase db = helper.getReadableDatabase();
		db.beginTransaction();

		for (Iterator<Cidade> es = listaCidades.iterator(); es.hasNext();) {

			Cidade cidade = es.next();
			ContentValues valuesEspecialidade = valoresPorCidade(cidade);

			db.insert(Constantes.TABELA_CIDADE, null, valuesEspecialidade);
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}

	private Cidade preencherCidade(Cursor cursor) {

		Integer codigo = Integer.parseInt(cursor.getString(0));
		String nome = cursor.getString(1);

		Cidade cidade = new Cidade();
		cidade.setCodigoCidade(codigo);
		cidade.setNomeCidade(nome);
		
		return cidade;
	}

	public List<Cidade> listarCidades() {

		List<Cidade> listaCidades = new ArrayList<Cidade>();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from " + Constantes.TABELA_CIDADE
				+ " where 1=1", null);

		while (cursor.moveToNext()) {
			listaCidades.add(preencherCidade(cursor));
		}
		cursor.close();
		db.close();
		
		return listaCidades;
	}

	public int excluirTodasCidades() {

		SQLiteDatabase db = helper.getWritableDatabase();
		int rows = db.delete(Constantes.TABELA_CIDADE, null, null);
		db.close();

		return rows;
	}

}