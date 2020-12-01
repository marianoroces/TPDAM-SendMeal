package ar.com.marianoroces.sendmeal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;

import java.util.Random;

import ar.com.marianoroces.sendmeal.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng posicionLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.myMap=googleMap;
        actualizarMapa();

        myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                myMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Ubicacion Seleccionada")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(0x66FF0000);
                polylineOptions.add(latLng);
                polylineOptions.add(posicionLocal);

                myMap.addPolyline(polylineOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                builder.setMessage("Agregar Ubicacion?")
                        .setTitle("UBICACION")
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent ubicacion = new Intent();
                                ubicacion.putExtra("Latitud", latLng.latitude);
                                ubicacion.putExtra("Longitud", latLng.longitude);
                                setResult(RESULT_OK, ubicacion);
                                finish();
                            }
                        });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    private void actualizarMapa(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        myMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                LatLng ubicacion = new LatLng(location.getLatitude(), location.getLongitude());
                                myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));

                                Random r = new Random();

                                // Una direccion aleatoria de 0 a 359 grados
                                int direccionRandomEnGrados = r.nextInt(360);

                                // Una distancia aleatoria de 100 a 1000 metros
                                int distanciaMinima = 100;
                                int distanciaMaxima = 1000;
                                int distanciaRandomEnMetros = r.nextInt(distanciaMaxima - distanciaMinima) + distanciaMinima;

                                posicionLocal = SphericalUtil.computeOffset(
                                        ubicacion,
                                        distanciaRandomEnMetros,
                                        direccionRandomEnGrados
                                );

                                myMap.addMarker(new MarkerOptions()
                                .title("Local")
                                .position(posicionLocal)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            }
                        }
                    });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 9999:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    actualizarMapa();
                }
                return;
        }
    }
}