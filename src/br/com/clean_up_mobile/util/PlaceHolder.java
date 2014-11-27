package br.com.clean_up_mobile.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

/**
 * 
 * @author George Class to display the auto complete addresses , data fetching
 *         from
 * 
 *         google place api.
 * 
 * 
 */

public class PlaceHolder extends ArrayAdapter<String> implements

Filterable {

	// For Auto complete ..

	private ArrayList<String> resultList;

	private static final String LOG_TAG = "DemoAddress";
	
	private static final String GEO_LOCALIZACAO = "http://maps.googleapis.com/maps/api/geocode/json?language=pt-BR&address=";

	Context context;

	// Constructor

	public PlaceHolder(Context context, int textViewResourceId

	) {

		super(context, textViewResourceId);

		this.context = context;

	}

	@Override
	public int getCount() {

		return resultList.size();

	}

	@Override
	public String getItem(int position) {

		return resultList.get(position);

	}

	@Override
	public Filter getFilter() {

		Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				FilterResults filterResults = new FilterResults();

				if (constraint != null) {

					// Retrieve the auto complete results.

					resultList = autocomplete(constraint.toString());

					// Assign the data to filter results

					filterResults.values = resultList;

					filterResults.count = resultList.size();

				}

				return filterResults;

			}

			@Override
			protected void publishResults(CharSequence constraint,

			FilterResults results) {

				if (results != null && results.count > 0) {

					notifyDataSetChanged();

				} else {

					notifyDataSetInvalidated();

				}

			}

		};

		return filter;

	}

	// get the array list of addresses

	private ArrayList<String> autocomplete(String input) {

		ArrayList<String> resultList = null;

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
			resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				resultList.add(predsJsonArray.getJSONObject(i).getString("formatted_address"));
			}
			
			
		} catch (JSONException e) {

			Log.e(LOG_TAG, "Cannot process JSON results", e);

		}

		return resultList;

	}

}
