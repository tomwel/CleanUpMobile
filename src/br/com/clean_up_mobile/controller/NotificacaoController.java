package br.com.clean_up_mobile.controller;

import br.com.clean_up_mobile.R;
import br.com.clean_up_mobile.activity.LoginActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * 
 * Estudo: http://www.tutorialspoint.com/android/android_notifications.htm
 * 
 * */

public class NotificacaoController {

	// public static final int TIPO_ACTIVITY = 1;
	// public static final int TIPO_BROADCAST = 2;
	// public static final int TIPO_SERVICE = 3;
	// public static final String INTENT_STRING_MSG_STATUS = "MSGs";
	// public static final String INTENT_STRING_MSG = "MSG";
	// public static final String INTENT_STRING_TITULO = "titu";
	// public static final String INTENT_LONG_QUANDO = "WHEN";
	// public static final String INTENT_INT_ICONE = "icone";
	// public static final String INTENT_INT_FLAGS = "FLS";

	private NotificationManager mNotificationManager;
	private static int notificationID = 100;
	private int numMessages = 2;

	public void exibeNotificacao(Context context) {

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context);

		mBuilder.setContentTitle("New Message");
		mBuilder.setContentText("You've received new message.");
		mBuilder.setTicker("New Message Alert!");
		mBuilder.setSmallIcon(R.drawable.ic_launcher);

		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(notificationID, mBuilder.build());

	}

	public void atulizaNotificacao(Context context) {

		/* Invoking the default notification service */
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context);

		mBuilder.setContentTitle("Updated Message");
		mBuilder.setContentText("You've got updated message.");
		mBuilder.setTicker("Updated Message Alert!");
		mBuilder.setSmallIcon(R.drawable.ic_launcher);

		/* Increase notification number every time a new notification arrives */
		mBuilder.setNumber(++numMessages);

		/* Creates an explicit intent for an Activity in your app */
		Intent resultIntent = new Intent();

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(LoginActivity.class);

		/* Adds the Intent that starts the Activity to the top of the stack */
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(resultPendingIntent);

		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		/* Update the existing notification using same notification ID */
		mNotificationManager.notify(notificationID, mBuilder.build());
	}

	public void cancelaNotificacao() {
		mNotificationManager.cancel(notificationID);
	}
}