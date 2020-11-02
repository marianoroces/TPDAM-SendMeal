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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.marianoroces.sendmeal.enums.TipoEnvio;
import ar.com.marianoroces.sendmeal.model.Pedido;
import ar.com.marianoroces.sendmeal.model.PedidoPlato;
import ar.com.marianoroces.sendmeal.repositories.PedidoPlatoRepository;
import ar.com.marianoroces.sendmeal.repositories.PedidoRepository;
import ar.com.marianoroces.sendmeal.utils.MyIntentService;
import ar.com.marianoroces.sendmeal.utils.MyReceiver;
import ar.com.marianoroces.sendmeal.adapters.PlatoPedidoAdapter;
import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;
import ar.com.marianoroces.sendmeal.utils.OnPedidoPlatoResultCallback;
import ar.com.marianoroces.sendmeal.utils.OnPedidoResultCallback;

public class PedidoActivity extends AppCompatActivity implements OnPedidoResultCallback, OnPedidoPlatoResultCallback {

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
    ArrayList<PedidoPlato> listaPlatosPedidos = new ArrayList<PedidoPlato>();
    int CODIGO_AGREGAR_PLATO = 1;
    Double totalPedido = 0.00;
    ConfirmarPedidoTask confirmarTask;
    BroadcastReceiver myReceiver;
    PedidoRepository pedidoRepository;
    PedidoPlatoRepository pedidoPlatoRepository;
    Pedido pedido;

    public static final String CODIGO_PEDIDO_CONFIRMADO = "1";
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        pedidoRepository = new PedidoRepository(this.getApplication(), this);
        pedidoPlatoRepository = new PedidoPlatoRepository(this.getApplication(), this);
        pedido = new Pedido();
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
                platoAuxiliar.setId(data.getStringExtra("idPlato"));
                PedidoPlato platoPedidoAux = new PedidoPlato();
                platoPedidoAux.setPlato(platoAuxiliar);

                listaPlatosPedidos.add(platoPedidoAux);
                agregarPlatosPedidos(platoAuxiliar);

                if(listaPlatos.size() > 0) {
                    btnConfirmarPedido.setEnabled(true);
                }

                totalPedido += platoAuxiliar.getPrecio();
                txtPrecioTotal.setText("Total: $"+String.valueOf(totalPedido));
            }
        }
    }

    private void agregarPlatosPedidos(Plato platoAux) {
        listaPlatos.add(platoAux);
        platoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResult(Pedido pedido) {

    }

    @Override
    public void onResult(PedidoPlato pedidoPlato) {

    }

    class ConfirmarPedidoTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String tipoEnvioTemp = spTipoPedido.getSelectedItem().toString();
            String tipoEnvio = "";
            switch(tipoEnvioTemp){
                case "Envio":
                    tipoEnvio = "ENVIO";
                    break;
                case "Take Away":
                    tipoEnvio = "TAKE_AWAY";
                    break;
            }

            pedido.setTipoEnvio(TipoEnvio.valueOf(tipoEnvio));
            pedido.setFecha(new Date());
            pedido.setEmail(txtMail.getText().toString());
            pedido.setCalle(txtCalle.getText().toString());
            pedido.setNumero(txtNumero.getText().toString());
            pedido.setPrecio(totalPedido);
            pedido.setPlatosPedidos(listaPlatosPedidos);

            /*PARA GUARDAR PEDIDO CON ROOM
            Long idPedidoPlato = pedidoRepository.insertar(pedido);
            for(PedidoPlato pedidoPlatoAux : listaPlatosPedidos){
                pedidoPlatoAux.setIdPedido(idPedidoPlato);
                pedidoPlatoRepository.insertar(pedidoPlatoAux);
            }*/

            //PARA GUARDAR PEDIDO CON RETROFIT
            pedidoRepository.insertarRest(pedido);

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