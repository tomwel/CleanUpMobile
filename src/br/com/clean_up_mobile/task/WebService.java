package br.com.clean_up_mobile.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import br.com.clean_up_mobile.util.Util;

import android.util.Log;

public class WebService {

	public static String getREST(String url) {

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
		} catch (IOException e) {
			// Log.e("CLUP", "Falha ao acessar Web service", e);
			return null;
		}
		return null;
	}

	public static String getREST(String url, Object o) {

		URL u = null;
		HttpURLConnection conexao = null;
		String json = null;

		try {

			json = Util.convertJSON(o);
			
			Log.v("CLUP", "json" + json);

			u = new URL(url);

			conexao = (HttpURLConnection) u.openConnection();

			conexao.setRequestMethod("POST");
			conexao.addRequestProperty("Content-type", "application/json");
			conexao.setReadTimeout(10000);
			conexao.setConnectTimeout(20000);

			conexao.setDoOutput(true);

			conexao.connect();

			OutputStream os = conexao.getOutputStream();
			os.write(json.getBytes());
			os.flush();

			if (conexao.getResponseCode() == 200) {
				InputStream is = conexao.getInputStream();

				return toString(is);
			} else {
				// pode ser erro 403 ou 404 ou 409
				 Log.v("CLUP", "erro" + conexao.getResponseCode());
				return null;
			}

		} catch (IOException e) {
			 Log.e("CLUP", "Falha ao acessar Web service classe web", e);
			return null;
		} finally {
			conexao.disconnect();
		}

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