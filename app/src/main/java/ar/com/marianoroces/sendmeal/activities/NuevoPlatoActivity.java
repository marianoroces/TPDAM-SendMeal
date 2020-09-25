package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;

public class NuevoPlatoActivity extends AppCompatActivity {

    EditText txtTituloPlato;
    EditText txtDescripcionPlato;
    EditText txtPrecioPlato;
    EditText txtCaloriasPlato;
    Button btnGuardarPlato;
    Toolbar tbNuevoPlato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_plato);

        txtTituloPlato = findViewById(R.id.txtTituloPlato);
        txtDescripcionPlato = findViewById(R.id.txtDescripcionPlato);
        txtPrecioPlato = findViewById(R.id.txtPrecioPlato);
        txtCaloriasPlato = findViewById(R.id.txtCaloriasPlato);
        btnGuardarPlato = findViewById(R.id.btnGuardarPlato);

        tbNuevoPlato = findViewById(R.id.tbNuevoPlato);
        setSupportActionBar(tbNuevoPlato);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnGuardarPlato.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(txtTituloPlato.getText().toString().equals("")) {
                    txtTituloPlato.setError("INGRESAR TITULO DEL PLATO");
                } else {
                    if(txtDescripcionPlato.getText().toString().equals("")){
                        txtDescripcionPlato.setError("INGRESAR DESCRIPCION DEL PLATO");
                    } else {
                        if(txtPrecioPlato.getText().toString().equals("")) {
                            txtPrecioPlato.setError("INGRESAR PRECIO DEL PLATO");
                        } else {
                            if(txtCaloriasPlato.getText().toString().equals("")){
                                txtCaloriasPlato.setError("INGRESAR CALORIAS DEL PLATO");
                            } else {
                                Plato plato = new Plato(txtTituloPlato.getText().toString(),
                                                        txtDescripcionPlato.getText().toString(),
                                                        Double.parseDouble(txtPrecioPlato.getText().toString()),
                                                        Integer.parseInt(txtCaloriasPlato.getText().toString()));

                                txtTituloPlato.setText("");
                                txtDescripcionPlato.setText("");
                                txtPrecioPlato.setText("");
                                txtCaloriasPlato.setText("");

                                Toast.makeText(NuevoPlatoActivity.this, "NUEVO PLATO CREADO", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }
}