package br.com.clean_up_mobile.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String NOME_BANCO = "cleanupDB";
	private static final int VERSAO_BANCO = 1;

	public DBHelper(Context context) {
		super(context, NOME_BANCO, null, VERSAO_BANCO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create TABLE usuario (id INTEGER PRIMARY KEY,"
				+ "email TEXT, ativo INTEGER, apelido TEXT,"
				+ "senha TEXT, perfil TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Utilizar s— na proxima vers‹o :)

	}
}
