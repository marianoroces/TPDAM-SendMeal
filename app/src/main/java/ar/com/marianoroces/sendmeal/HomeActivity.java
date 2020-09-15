package ar.com.marianoroces.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
                Intent registrarmeIntent = new Intent(HomeActivity.this, RegistrarseActivity.class);
                startActivity(registrarmeIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}