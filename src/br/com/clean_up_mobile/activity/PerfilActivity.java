package br.com.clean_up_mobile.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.model.Usuario;
import br.com.clean_up_mobile.task.ClienteVOHttp;
import br.com.clean_up_mobile.task.DiaristaPerfilVOHttp;
import br.com.clean_up_mobile.task.WebService;
import br.com.clean_up_mobile.util.Constantes;
import br.com.clean_up_mobile.util.Util;
import br.com.clean_up_mobile.vo.ClienteVO;
import br.com.clean_up_mobile.vo.DiaristaPerfilVO;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PerfilActivity extends ActionBarActivity implements
		OnClickListener {
	ProgressDialog prgDialog;
	ClienteVO clienteVO;
	DiaristaPerfilVO diaristaVO;
	Usuario usuario;
	ImageView imgPerfil;
	ClienteVOTask mTaskCliente;
	DiaristaVOTask mTaskDiarista;
	EditText nomeUsuario;
	Button btnEditarDados;
	ImageButton buttonLoadImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);

		usuario = (Usuario) getIntent().getSerializableExtra("usuario");

		nomeUsuario = (EditText) findViewById(R.id.editTextNome);
		imgPerfil = (ImageView) findViewById(R.id.imageViewPhotoPerfil);

		btnEditarDados = (Button) findViewById(R.id.buttonAlterarDados);
		btnEditarDados.setOnClickListener(btnAlterarDadosOnClickListener);

		buttonLoadImage = (ImageButton) findViewById(R.id.imageButtonEditarPhoto);
		buttonLoadImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				selectImage();
			}
		});
		iniciarDownload();
	}

	private OnClickListener btnAlterarDadosOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			iniciarDownload();
			if (usuario.getPerfil().equals("ROLE_CLIENT")) {
				Intent editarDadosClienteIntent = new Intent(
						getApplicationContext(),
						EditarPerfilClienteActivity.class);
				startActivity(editarDadosClienteIntent);
				finish();

			} else if (usuario.getPerfil().equals("ROLE_DIARIST")) {
				Intent editarDadosDiaristaIntent = new Intent(
						getApplicationContext(),
						EditarPerfilDiaristaActivity.class);
				startActivity(editarDadosDiaristaIntent);
				finish();
			}
		}
	};

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Gallery",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				PerfilActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, 1);
				} else if (items[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				File f = new File(Environment.getExternalStorageDirectory()
						.toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}
				try {
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

					bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
							bitmapOptions);
					bitmap = Bitmap.createScaledBitmap(bitmap, 433, 398, true);
					if (usuario.getPerfil().equals("ROLE_CLIENT")) {
						clienteVO.setFotoUsuario(encodeTobase64(bitmap));
						clienteVO.setCodUsuario(usuario.getId());
						alterarPerfilCliente(clienteVO);
					} else if (usuario.getPerfil().equals("ROLE_DIARIST")) {
						diaristaVO.setFotoUsuario(encodeTobase64(bitmap));
						diaristaVO.setUsuario(usuario);
						alterarPerfilDiarista(diaristaVO);
					}
					imgPerfil.setImageBitmap(bitmap);
					String path = android.os.Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "Phoenix" + File.separator + "default";
					f.delete();
					OutputStream outFile = null;
					File file = new File(path, String.valueOf(System
							.currentTimeMillis()) + ".jpg");
					try {
						outFile = new FileOutputStream(file);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

						outFile.flush();
						outFile.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (requestCode == 2) {

				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getContentResolver().query(selectedImage, filePath,
						null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				String picturePath = c.getString(columnIndex);
				c.close();
				Bitmap bm = (BitmapFactory.decodeFile(picturePath));
				Log.w("path of image from gallery......******************.........",
						picturePath + "");
				bm = Bitmap.createScaledBitmap(bm, 433, 398, true);
				if (usuario.getPerfil().equals("ROLE_CLIENT")) {
					clienteVO.setFotoUsuario(encodeTobase64(bm));
					clienteVO.setCodUsuario(usuario.getId());
					alterarPerfilCliente(clienteVO);
				} else if (usuario.getPerfil().equals("ROLE_DIARIST")) {
					diaristaVO.setFotoUsuario(encodeTobase64(bm));
					diaristaVO.setUsuario(usuario);
					alterarPerfilDiarista(diaristaVO);
				}
				imgPerfil.setImageBitmap(bm);
			}
		}
	}

	private void iniciarDownload() {

		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected()) {
			if (usuario.getPerfil().equals("ROLE_CLIENT")) {
				mTaskCliente = new ClienteVOTask();
				mTaskCliente.execute();
			} else if (usuario.getPerfil().equals("ROLE_DIARIST")) {
				mTaskDiarista = new DiaristaVOTask();
				mTaskDiarista.execute();
			}
		}
	}

	private void mostrarProgressDialog(String texto) {
		prgDialog = new ProgressDialog(PerfilActivity.this);
		prgDialog.setMessage(texto);
		prgDialog.setTitle("Cadastro");
		prgDialog.setCancelable(true);
		prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		prgDialog.show();
	}

	public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public static String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = "data:image/png;base64,"
				+ Base64.encodeToString(b, Base64.DEFAULT);

		Log.e("LOOK", imageEncoded);
		return imageEncoded;
	}

	public static Bitmap decodeBase64(String input) {
		int inicio;
		int fim;
		inicio = input.indexOf(",");
		fim = input.length();
		String imgBase64 = input.substring(inicio + 1, fim);
		byte[] decodedByte = Base64.decode(imgBase64, Base64.DEFAULT);
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	class ClienteVOTask extends AsyncTask<Void, Void, ClienteVO> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgressDialog("Atualizando informações.");
		}

		@Override
		protected ClienteVO doInBackground(Void... params) {
			try {
				return ClienteVOHttp.retrieveCliente(usuario.getId());
			} catch (IOException i) {
				i.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ClienteVO result) {
			super.onPostExecute(result);
			if (prgDialog.isShowing()) {
				prgDialog.dismiss();
			}
			if (result != null) {
				clienteVO = result;
				if (!clienteVO.getFotoUsuario().equals(
						"/cleanUp/resources/assets/img/avatar.jpg")) {
					Bitmap bitmap;
					bitmap = decodeBase64(clienteVO.getFotoUsuario());
					bitmap = Bitmap.createScaledBitmap(bitmap, 433, 398, true);
					imgPerfil.setImageBitmap(bitmap);
					nomeUsuario.setText(clienteVO.getNome().toString());
				}
				
			} else {
				Util.criarToast(getApplicationContext(),
						R.string.msgDeErroWebservice);
			}
		}
	}

	class DiaristaVOTask extends AsyncTask<Void, Void, DiaristaPerfilVO> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mostrarProgressDialog("Atualizando informações.");
		}

		@Override
		protected DiaristaPerfilVO doInBackground(Void... params) {
			try {
				return DiaristaPerfilVOHttp.retrieveDiarista(usuario.getId());
			} catch (IOException i) {
				i.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(DiaristaPerfilVO result) {
			super.onPostExecute(result);
			if (prgDialog.isShowing()) {
				prgDialog.dismiss();
			}
			if (result != null) {
				diaristaVO = result;
				if (!diaristaVO.getFotoUsuario().equals(
						"/cleanUp/resources/assets/img/avatar.jpg")) {
					Bitmap bitmap;
					bitmap = decodeBase64(diaristaVO.getFotoUsuario());
					bitmap = Bitmap.createScaledBitmap(bitmap, 433, 398, true);
					imgPerfil.setImageBitmap(bitmap);
					nomeUsuario.setText(diaristaVO.getUsuario().getApelido()
							.toString());
				}
			} else {
				Util.criarToast(getApplicationContext(),
						R.string.msgDeErroWebservice);
			}
		}
	}

	public void alterarPerfilCliente(ClienteVO clienteVO) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTaskCliente(Constantes.POST_ATUALIZARPERFILCLIENTE,
					clienteVO).execute();
	}

	public void alterarPerfilDiarista(DiaristaPerfilVO diaristaVO) {
		if (Util.existeConexao(getApplicationContext()))
			new HttpAsyncTaskDiarista(Constantes.POST_ATUALIZARPERFILDIARISTA,
					diaristaVO).execute();
	}

	private class HttpAsyncTaskCliente extends AsyncTask<Void, Void, String> {

		private Object clienteVO;
		private String url;

		public HttpAsyncTaskCliente(String url, Object clienteVo) {
			this.clienteVO = clienteVo;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, clienteVO);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

				try {
					Util.criarToast(getApplicationContext(),
							R.string.msgSucessoPerfil);
				} catch (Exception e) {
					Util.criarToast(getApplicationContext(),
							R.string.msgDeErroWebservice);
					e.printStackTrace();
				}
			}
		}
	}

	private class HttpAsyncTaskDiarista extends AsyncTask<Void, Void, String> {

		private Object diaristaVO;
		private String url;

		public HttpAsyncTaskDiarista(String url, Object diaristaVO) {
			this.diaristaVO = diaristaVO;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(Void... params) {
			return WebService.getREST(url, diaristaVO);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {

				try {
					Util.criarToast(getApplicationContext(),
							R.string.msgSucessoPerfil);
				} catch (Exception e) {
					Util.criarToast(getApplicationContext(),
							R.string.msgDeErroWebservice);
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
