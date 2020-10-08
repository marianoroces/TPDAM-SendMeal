package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import ar.com.marianoroces.sendmeal.adapters.PlatoRecyclerAdapter;
import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;

public class ListaPlatosActivity extends AppCompatActivity {

    Toolbar tbListaPlatos;
    RecyclerView rvListaPlatos;
    PlatoRecyclerAdapter platoAdapter;
    List<Plato> listaPlatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platos);

        tbListaPlatos = findViewById(R.id.tbListaPlatos);
        setSupportActionBar(tbListaPlatos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaPlatos = new ArrayList<Plato>();
        inicializarPlatos(listaPlatos);

        platoAdapter = new PlatoRecyclerAdapter(listaPlatos, ListaPlatosActivity.this);

        rvListaPlatos = findViewById(R.id.rvListaPlatos);
        rvListaPlatos.setAdapter(platoAdapter);
        rvListaPlatos.setLayoutManager(new LinearLayoutManager(this));
        rvListaPlatos.setHasFixedSize(true);

        platoAdapter.activarBotones(false);

        if(getIntent().getStringExtra("iniciadoDesde").equalsIgnoreCase("pedido")){
            platoAdapter.activarBotones(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listar_plato_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuNuevoPedido:
                Intent nuevoPedidoIntent = new Intent(ListaPlatosActivity.this, PedidoActivity.class);
                startActivity(nuevoPedidoIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializarPlatos(List<Plato> platos) {
        platos.add(new Plato("Plato 1", "descripcion 1", 300.15, 200));
        platos.add(new Plato("Plato 2", "descripcion 2", 135.98, 120));
        platos.add(new Plato("Plato 3", "descripcion 3", 500.00, 80));
        platos.add(new Plato("Plato 4", "descripcion 4", 1854.22, 543));
        platos.add(new Plato("Plato 5", "descripcion 5", 753.44, 178));
        platos.add(new Plato("Plato 6", "descripcion 6", 50.35, 260));
    }
}