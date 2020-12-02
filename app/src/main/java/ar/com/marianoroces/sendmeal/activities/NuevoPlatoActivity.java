package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;

import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;
import ar.com.marianoroces.sendmeal.repositories.PlatoRepository;
import ar.com.marianoroces.sendmeal.utils.OnPlatoResultCallback;

public class NuevoPlatoActivity extends AppCompatActivity implements OnPlatoResultCallback{

    EditText txtTituloPlato;
    EditText txtDescripcionPlato;
    EditText txtPrecioPlato;
    EditText txtCaloriasPlato;
    Button btnGuardarPlato;
    Button btnSacarFoto;
    Toolbar tbNuevoPlato;
    PlatoRepository platoRepository;
    StorageReference platosImagesRef;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    byte[] datos;
    final Plato plato = new Plato();

    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_plato);

        platoRepository = new PlatoRepository(this.getApplication(), this);

        txtTituloPlato = findViewById(R.id.txtTituloPlato);
        txtDescripcionPlato = findViewById(R.id.txtDescripcionPlato);
        txtPrecioPlato = findViewById(R.id.txtPrecioPlato);
        txtCaloriasPlato = findViewById(R.id.txtCaloriasPlato);
        btnGuardarPlato = findViewById(R.id.btnGuardarPlato);
        btnSacarFoto = findViewById(R.id.btnSacarFoto);

        tbNuevoPlato = findViewById(R.id.tbNuevoPlato);
        setSupportActionBar(tbNuevoPlato);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSacarFoto.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarCamara();
            }
        });

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

                                plato.setTitulo(txtTituloPlato.getText().toString());
                                plato.setDescripcion(txtDescripcionPlato.getText().toString());
                                plato.setPrecio(Double.parseDouble(txtPrecioPlato.getText().toString()));
                                plato.setCalorias(Integer.parseInt(txtCaloriasPlato.getText().toString()));

                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        //PARA INSERTAR CON ROOM
                                        //platoRepository.insertar(plato);

                                        //PARA INSERTAR CON RETROFIT
                                        platoRepository.insertarRest(plato);


                                        Log.d("DEBUG", plato.getId()+" - "+plato.getTitulo());
                                    }
                                });

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

    @Override
    public void onResult(List<Plato> plato) {

    }

    @Override
    public void onResult(Plato plato) {

    }

    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

    /*
    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALERIA_REQUEST);
    }
    */

    private void storage() {
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Creamos una referencia a 'images/plato_id.jpg'
        platosImagesRef = storageRef.child("images/plato_id.jpg");
    }

    private void guardarFotoPlato() {
        // Cual quiera de los tres métodos tienen la misma implementación, se debe utilizar el que corresponda
        UploadTask uploadTask = platosImagesRef.putBytes(datos);
        // UploadTask uploadTask = platosImagesRef.putFile(file);
        // UploadTask uploadTask = platosImagesRef.putStream(stream);

        // Registramos un listener para saber el resultado de la operación
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continuamos con la tarea para obtener la URL
                return platosImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // URL de descarga del archivo
                    Uri downloadUri = task.getResult();
                    Log.d("DEBUG", "URI: "+downloadUri.toString());
                    plato.setUriImagen(downloadUri.toString());
                } else {
                    // Fallo
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMARA_REQUEST || requestCode == GALERIA_REQUEST) && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            datos = baos.toByteArray(); // Imagen en arreglo de bytes
            //Log.d("DEBUG", "Imagen: "+datos.toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(NuevoPlatoActivity.this);
            builder.setMessage("Agregar Foto al plato?")
                    .setTitle("Nuevo Plato")
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            storage();
                            guardarFotoPlato();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.show();
        }
    }
}
