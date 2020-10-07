package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

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
                startActivityForResult(intentAgregarPlato, CODIGO_AGREGAR_PLATO);
            }
        });

        btnConfirmarPedido.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PedidoActivity.this, "Pedido confirmado", Toast.LENGTH_LONG).show();
                finish();
            }
        });
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

                totalPedido += platoAuxiliar.getPrecio();
                txtPrecioTotal.setText("Total: $"+String.valueOf(totalPedido));
            }
        }
    }

    private void agregarPlato(Plato platoAux){
        listaPlatos.add(platoAux);
        platoAdapter.notifyDataSetChanged();
    }
}