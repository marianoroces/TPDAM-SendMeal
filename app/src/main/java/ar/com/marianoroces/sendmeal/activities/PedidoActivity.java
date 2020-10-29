package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.marianoroces.sendmeal.utils.MyIntentService;
import ar.com.marianoroces.sendmeal.utils.MyReceiver;
import ar.com.marianoroces.sendmeal.adapters.PlatoPedidoAdapter;
import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;

public class PedidoActivity extends AppCompatActivity {

    EditText txtMail;
    EditText txtCalle;
    EditText txtNumero;
    TextView txtPrecioTotal;
    Spinner spTipoPedido;
    Toolbar toolbar;
    Button btnAgregarPlato;
    Button btnConfirmarPedido;
    ListView lvPedidos;
    PlatoPedidoAdapter platoAdapter;
    ArrayList<Plato> listaPlatos = new ArrayList<Plato>();
    int CODIGO_AGREGAR_PLATO = 1;
    Double totalPedido = 0.00;
    ConfirmarPedidoTask confirmarTask;
    BroadcastReceiver myReceiver;

    public static final String CODIGO_PEDIDO_CONFIRMADO = "1";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        confirmarTask = new ConfirmarPedidoTask();

        myReceiver = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_PEDIDO);
        this.registerReceiver(myReceiver, filtro);
        createNotificationChannel();

        txtMail = findViewById(R.id.txtMailNuevoPedido);
        txtCalle = findViewById(R.id.txtCalleNuevoPedido);
        txtNumero = findViewById(R.id.txtNumeroNuevoPedido);
        txtPrecioTotal = findViewById(R.id.txtPrecioTotal);
        spTipoPedido = findViewById(R.id.spTipoPedidoNuevoPedido);
        toolbar = findViewById(R.id.tbNuevoPedido);
        btnAgregarPlato = findViewById(R.id.btnAgregarPlatos);
        btnConfirmarPedido = findViewById(R.id.btnConfirmarPedido);
        lvPedidos = findViewById(R.id.lvNuevoPedido);

        platoAdapter = new PlatoPedidoAdapter(this, listaPlatos);
        lvPedidos.setAdapter(platoAdapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] tipoPedido = {"Envio", "Take Away"};
        ArrayAdapter<String> tipoPedidoAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tipoPedido);
        spTipoPedido.setAdapter(tipoPedidoAdapter);

        btnAgregarPlato.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAgregarPlato = new Intent(PedidoActivity.this, ListaPlatosActivity.class);
                intentAgregarPlato.putExtra("iniciadoDesde", "pedido");
                startActivityForResult(intentAgregarPlato, CODIGO_AGREGAR_PLATO);
            }
        });

        btnConfirmarPedido.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarTask.execute();
                finish();
            }
        });

        if(listaPlatos.size() == 0) {
            btnConfirmarPedido.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODIGO_AGREGAR_PLATO) {
            if(resultCode == Activity.RESULT_OK) {
                String nombrePlato = data.getStringExtra("nombrePlato");
                String precioPlato = data.getStringExtra("precioPlato");
                String caloriasPlato = data.getStringExtra("caloriasPlato");
                String descripcionPlato = data.getStringExtra("descripcionPlato");

                Plato platoAuxiliar = new Plato(nombrePlato, descripcionPlato, Double.parseDouble(precioPlato), Integer.parseInt(caloriasPlato));
                agregarPlato(platoAuxiliar);

                if(listaPlatos.size() > 0) {
                    btnConfirmarPedido.setEnabled(true);
                }

                totalPedido += platoAuxiliar.getPrecio();
                txtPrecioTotal.setText("Total: $"+String.valueOf(totalPedido));
            }
        }
    }

    private void agregarPlato(Plato platoAux) {
        listaPlatos.add(platoAux);
        platoAdapter.notifyDataSetChanged();
    }

    class ConfirmarPedidoTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            for(int i=0; i<5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Pedido confirmado";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intentNotificacion = new Intent(PedidoActivity.this, MyIntentService.class);
            intentNotificacion.putExtra("cuerpo", result);
            startService(intentNotificacion);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}