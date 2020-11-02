package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import ar.com.marianoroces.sendmeal.adapters.PlatoRecyclerAdapter;
import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;
import ar.com.marianoroces.sendmeal.repositories.PlatoRepository;
import ar.com.marianoroces.sendmeal.utils.OnPlatoResultCallback;

public class ListaPlatosActivity extends AppCompatActivity implements OnPlatoResultCallback {

    Toolbar tbListaPlatos;
    RecyclerView rvListaPlatos;
    PlatoRecyclerAdapter platoAdapter;
    List<Plato> listaPlatos;
    PlatoRepository platoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platos);

        platoRepository = new PlatoRepository(this.getApplication(), this);

        tbListaPlatos = findViewById(R.id.tbListaPlatos);
        setSupportActionBar(tbListaPlatos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaPlatos = new ArrayList<Plato>();

        //MOSTRAR LISTA DE PLATOS CON ROOM
        //platoRepository.buscarTodos();

        //MOSTRAR LISTA DE PLATOS UTILIZANDO API REST
        platoRepository.buscarTodosRest();
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

    @Override
    public void onResult(List<Plato> plato) {
        listaPlatos = plato;
        mostrarResultados(plato);

        platoAdapter = new PlatoRecyclerAdapter(listaPlatos, this);

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
    public void onResult(Plato plato) {

    }

    public void mostrarResultados(List<Plato> platos){
        Log.d("DEBUG", String.valueOf(platos.size()));
        for(Plato aux : platos){
            Log.d("DEBUG", "PLATO: "+aux.getTitulo()+" - ID: "+aux.getId());
        }
    }
}