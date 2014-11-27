package br.com.clean_up_mobile.task;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import br.com.clean_up_mobile.model.Endereco;

public class EnderecoHttp {
	private static final String LOG_TAG = "DemoAddress";
	private static final String GEO_LOCALIZACAO = "http://maps.googleapis.com/maps/api/geocode/json?language=pt-BR&address=";
	Context context;
	public static ArrayList<Endereco> autocomplete(String input) {

		ArrayList<Endereco> resultList = null;

		HttpURLConnection conn = null;

		StringBuilder jsonResults = new StringBuilder();

		try {

			StringBuilder sb = new StringBuilder(GEO_LOCALIZACAO);

			sb.append(URLEncoder.encode(input, "utf8"));

			URL url = new URL(sb.toString());

			conn = (HttpURLConnection) url.openConnection();

			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder

			int read;

			char[] buff = new char[1024];

			while ((read = in.read(buff)) != -1) {

				jsonResults.append(buff, 0, read);

			}

		} catch (MalformedURLException e) {

			Log.e(LOG_TAG, "Error processing Places API URL", e);

			return resultList;

		} catch (IOException e) {

			Log.e(LOG_TAG, "Error connecting to Places API", e);

			return resultList;

		} finally {

			if (conn != null) {

				conn.disconnect();

			}

		}

		try {

			// Create a JSON object hierarchy from the results

			JSONObject jsonObj = new JSONObject(jsonResults.toString());

			JSONArray predsJsonArray = jsonObj.getJSONArray("results");

			// Extract the Place descriptions from the results
			Endereco endereco = new Endereco();
			resultList = new ArrayList<Endereco>(predsJsonArray.length());
			JSONObject jsonLocation = new JSONObject();
			for (int i = 0; i < predsJsonArray.length(); i++) {

				endereco.setLogradouro((predsJsonArray.getJSONObject(i).getString("formatted_address")));
				
				JSONObject jsonGeometry = predsJsonArray.getJSONObject(i).getJSONObject("geometry");
				jsonLocation = jsonGeometry.getJSONObject("location");
				BigDecimal bigDecimalLng = new BigDecimal(jsonLocation.getString("lng"));
				BigDecimal bigDecimalLat = new BigDecimal(jsonLocation.getString("lat"));
				endereco.setLng(bigDecimalLng);
				endereco.setLat(bigDecimalLat);
				endereco.setLogradouro(predsJsonArray.getJSONObject(i).getString("formatted_address"));
				resultList.add(endereco);

			}
			
			
		} catch (JSONException e) {

			Log.e(LOG_TAG, "Cannot process JSON results", e);

		}

		return resultList;

	}

	// function to identify the country code by SIM.

	private String getCountryCode() {

		TelephonyManager tel = (TelephonyManager) context

		.getSystemService(Context.TELEPHONY_SERVICE);

		return tel.getNetworkCountryIso();

	}
}
