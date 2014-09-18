package br.com.clean_up_mobile.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
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

		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		int wifi = ConnectivityManager.TYPE_WIFI;
		int mobile = ConnectivityManager.TYPE_MOBILE;

		if (cm.getNetworkInfo(mobile).isConnected()
				|| cm.getNetworkInfo(wifi).isConnected()) {
			return true;
		} else {
			Toast.makeText(c, "Sem conexão com a internet", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

	}

}