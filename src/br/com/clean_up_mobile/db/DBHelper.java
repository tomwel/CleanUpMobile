package br.com.clean_up_mobile.db;

import br.com.clean_up_mobile.util.Constantes;
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
				+ "senha TEXT, perfil TEXT, fotoPerfil TEXT)");


		// tabela de especialidade
		db.execSQL("create TABLE especialidade (codigo INTEGER PRIMARY KEY,"
				+ "nome TEXT)");

		// tabela de ralacionamento diarista x especialidade
		db.execSQL("create TABLE relacionamento_especialidade_diarista (id INTEGER PRIMARY KEY,"
				+ "diarista INTEGER, especialidade INTEGER)");

		// tabela diarista favorita
				db.execSQL("create TABLE diarista_favorita (codigo INTEGER PRIMARY KEY,"
						+ "nome TEXT, telefone TEXT, cidade TEXT, mediaDiarista TEXT, fotoDiarista TEXT)");
		
		// tabela servico
		db.execSQL("create TABLE servico (codigo INTEGER PRIMARY KEY,"
				+ "tipo TEXT, descricao TEXT, cliente TEXT, diarista TEXT,"
				+ "endereco TEXT, data TEXT, valor TEXT, status TEXT, avaliacao INT, comentario TEXT)");
		
		// tabela cidade
				db.execSQL("create TABLE " + Constantes.TABELA_CIDADE
						+ " (codigo INTEGER PRIMARY KEY," + "nome TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Utilizar s� na proxima vers�o :)

	}
}
