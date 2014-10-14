package br.com.clean_up_mobile.db;

import br.com.clean_up_mobile.model.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDB {
	private DBHelper helper;

	public UsuarioDB(Context contexto) {
		helper = new DBHelper(contexto);
	}

	public long inserir(Usuario usuario) {
		;
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = valoresPorUsuario(usuario);

		long id = db.insert("usuario", null, values);
		usuario.setId((int) id);

		db.close();

		return id;
	}

	public int excluir(Usuario usuario) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int rows = db.delete("usuario", "id = " + usuario.getId(), null);
		db.close();

		return rows;
	}

	private ContentValues valoresPorUsuario(Usuario usuario) {
		ContentValues values = new ContentValues();
		values.put("id", usuario.getId());
		values.put("email", usuario.getEmail());
		values.put("apelido", usuario.getApelido());
		values.put("ativo", usuario.getAtivo());
		values.put("senha", usuario.getSenha());
		values.put("perfil", usuario.getPerfil());
		return values;
	}

	public boolean listaUsuario(Usuario usuario) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from usuario where email = ?",
				new String[] { usuario.getEmail() });

		boolean resultado = false;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			usuario.setId((int) cursor.getLong(cursor.getColumnIndex("id")));
			resultado = true;
		}
		cursor.close();
		db.close();
		return resultado;
	}

	public Usuario listaUsuario() {
		Usuario usuario = new Usuario();

		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from usuario where 1=1", null);

		while (cursor.moveToNext()) {
			usuario = preencherUsuario(cursor);
		}
		cursor.close();
		db.close();
		return usuario;
	}

	private Usuario preencherUsuario(Cursor cursor) {
		int id = cursor.getInt(0);
		String email = cursor.getString(1);
		boolean ativo = cursor.getInt(2) == 0;
		String apelido = cursor.getString(3);
		String senha = cursor.getString(4);
		String perfil = cursor.getString(5);
		Usuario usuario = new Usuario(id, email, apelido, ativo, senha, perfil);

		return usuario;
	}
}
