package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ar.com.marianoroces.sendmeal.R;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "BEBUG";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbarBienvenido = findViewById(R.id.tbBienvenido);
        setSupportActionBar(toolbarBienvenido);

        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();
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

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Exito
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // Error
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(HomeActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}