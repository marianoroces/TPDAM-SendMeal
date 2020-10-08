package ar.com.marianoroces.sendmeal;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent notificacion = new Intent();
        notificacion.putExtra("cuerpo", intent.getStringExtra("cuerpo"));
        notificacion.setAction(MyReceiver.EVENTO_PEDIDO);
        sendBroadcast(notificacion);
        this.sendBroadcast(notificacion);
    }
}
