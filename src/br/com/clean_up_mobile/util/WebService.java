package br.com.clean_up_mobile.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class WebService {

	public static String getRESTFileContent(String url) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = toString(instream);

				instream.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			Log.e("CHTTP", "Falha ao acessar Web service", e);

		} catch (Exception e) {

			Log.e("NGVL", "Falha ao acessar Web service", e);
			// statusCode = response.getStatusLine().getStatusCode();
			//
			// if (statusCode == 404) {
			// Toast.makeText(getApplicationContext(),
			// "Requested resource not found", Toast.LENGTH_LONG)
			// .show();
			// }
			// // When Http response code is '500'
			// else if (statusCode == 500) {
			// Toast.makeText(getApplicationContext(),
			// "Something went wrong at server end", Toast.LENGTH_LONG)
			// .show();
			// }
			// // When Http response code other than 404, 500
			// else {
			// Toast.makeText(
			// getApplicationContext(),
			// "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
			// Toast.LENGTH_LONG).show();
			// }
		}
		return null;
	}

	private static String toString(InputStream is) throws IOException {

		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}
}
