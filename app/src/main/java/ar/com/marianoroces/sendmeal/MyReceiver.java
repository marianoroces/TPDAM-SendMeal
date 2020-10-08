package ar.com.marianoroces.sendmeal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import ar.com.marianoroces.sendmeal.activities.HomeActivity;
import ar.com.marianoroces.sendmeal.activities.ListaPlatosActivity;

public class MyReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public static final String EVENTO_PEDIDO = "ar.com.marianoroces.sendmeal.EVENTO_PEDIDO";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equalsIgnoreCase(EVENTO_PEDIDO)){
            enviarNotificacion(context, intent);
        }
    }

    private void enviarNotificacion(Context context, Intent intent){
        Intent intentDestino = new Intent(context, HomeActivity.class);
        intentDestino.putExtra("iniciadoDesde", "notificacion");
        intentDestino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent destinoPending = PendingIntent.getActivity(context, 0, intentDestino, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Exito")
                .setContentText(intent.getStringExtra("cuerpo"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(destinoPending)
                .setAutoCancel(true);
        Notification notification = builder.build();

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify(99, notification);
    }
}
