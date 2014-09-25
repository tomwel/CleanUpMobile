package br.com.clean_up_mobile.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 *  * Class which has Utility methods  *  
 */
public class Util {

	private static Pattern pattern;
	private static Matcher matcher;
	// Email Pattern
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 *      * Validate Email with regular expression      *      * @param email
	 *      * @return true for Valid Email and false for Invalid Email      
	 */
	public static boolean validate(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 *      * Checks for Null String object      *      * @param txt      * @return
	 * true for not null and false for null String object      
	 */
	public static boolean isNotNull(String txt) {
		return txt != null && txt.trim().length() > 0 ? true : false;
	}

	public static String convertJSON(Object o) {

		Gson gson = new Gson();
		String json = gson.toJson(o);

		return json;
	}

	public static boolean existeConexao(Context c) {

		// ConnectivityManager cm = (ConnectivityManager) c
		// .getSystemService(Context.CONNECTIVITY_SERVICE);
		//
		// int wifi = ConnectivityManager.TYPE_WIFI;
		// int mobile = ConnectivityManager.TYPE_MOBILE;
		//
		// if (cm.getNetworkInfo(mobile).isConnected()
		// || cm.getNetworkInfo(wifi).isConnected()) {
		// return true;
		// } else {
		// Toast.makeText(c, "Sem conexão com a internet", Toast.LENGTH_SHORT)
		// .show();
		// return false;
		// }
		return true;
	}

	public static boolean check(String documento) {
		documento = Mask.unmask(documento);
		if (documento.length() == 11)
			return checkCPF(documento);
		// if (documento.length() == 14)
		// return ValidarDocumento.checkCNPJ(documento);
		return false;
	}

	private static boolean checkCPF(String CPF) {
		if (CPF.equals("00000000000") || CPF.equals("11111111111")
				|| CPF.equals("22222222222") || CPF.equals("33333333333")
				|| CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777")
				|| CPF.equals("88888888888") || CPF.equals("99999999999")) {
			return false;
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {

				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (Exception erro) {
			return (false);
		}

	}

	public static void criarToast(Context c, String mensagem) {
		Toast.makeText(c, mensagem, Toast.LENGTH_LONG).show();
	}

	public static void criarToast(Context c, int mensagem) {
		Toast.makeText(c, mensagem, Toast.LENGTH_LONG).show();
	}

	public static String limpaCpf(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		return cpf;
	}

	public static String limpaTelefone(String telefone) {
		telefone = telefone.replace("(", "");
		telefone = telefone.replace(")", "");
		telefone = telefone.replace("-", "");
		return telefone;
	}

	public static void criarAlertSimples(Context c, String titulo,
			String mensagem) {

		AlertDialog alerta;

		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setTitle(titulo);
		builder.setMessage(mensagem);

		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				// Toast.makeText(MainActivity.this, "positivo=" + arg1,
				// Toast.LENGTH_SHORT).show();
			}
		});

		alerta = builder.create();
		alerta.show();
	}

}