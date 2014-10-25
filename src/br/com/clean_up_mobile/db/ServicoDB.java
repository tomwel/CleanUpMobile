package br.com.clean_up_mobile.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.clean_up_mobile.model.Cliente;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Servico;
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

	public long inserir(Servico servico) {

		SQLiteDatabase db = helper.getWritableDatabase();
		DiaristaDB diaristaDB = new DiaristaDB(contexto);
		ClienteDB clienteDB = new ClienteDB(contexto);

		ContentValues values = valoresPorServico(servico);
		long id = db.insert("servico", null, values);
		servico.setCodServico((int) id);

		// Cadastra a diarista
		clienteDB.inserir(servico.getCliente());
		// cadastra o cliente
		diaristaDB.inserir(servico.getDiarista());

		db.close();

		return id;
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

		int rows = db.delete("servico", "id = " + servico.getCodServico(), null);
		db.close();

		return rows;
	}

	
	public Servico  pegarServico(Integer codigoDoServico) throws ParseException {

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
	
	

	public List<Servico> listaServico() throws ParseException {
		
		List<Servico> listaServico = null;
		
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
		DiaristaDB  diaristaDB = new DiaristaDB(contexto);
		
		
		Integer codigo = cursor.getInt(0);
		String tipo = cursor.getString(1);
		String descricao = cursor.getString(2);
		Integer codigoDoCliente = cursor.getInt(3);
		Integer codigoDaDiarista = cursor.getInt(4);
		String endereco = cursor.getString(5);
		Date data = formatter.parse(cursor.getString(6));
		double valor = Double.parseDouble(cursor.getString(7));
		String status = cursor.getString(8);
		
		Cliente cliente = clienteDB.pegarCliente(codigoDoCliente);
		Diarista diarista = diaristaDB.pegarDiarista(codigoDaDiarista);
		
		Servico servico = new Servico(codigo, tipo, descricao, cliente, diarista, null, data, valor, status);

		return servico;
	}
}
