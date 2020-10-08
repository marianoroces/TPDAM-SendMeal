package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ar.com.marianoroces.sendmeal.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbarBienvenido = findViewById(R.id.tbBienvenido);
        setSupportActionBar(toolbarBienvenido);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRegistrarme:
                Intent registrarseIntent = new Intent(HomeActivity.this, RegistrarseActivity.class);
                startActivity(registrarseIntent);
                break;
            case R.id.menuCrearPlato:
                Intent nuevoPlatoIntent = new Intent(HomeActivity.this, NuevoPlatoActivity.class);
                startActivity(nuevoPlatoIntent);
                break;
            case R.id.menuListaPlatos:
                Intent listaPlatosIntent = new Intent(HomeActivity.this, ListaPlatosActivity.class);
                listaPlatosIntent.putExtra("iniciadoDesde", "home");
                startActivity(listaPlatosIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}