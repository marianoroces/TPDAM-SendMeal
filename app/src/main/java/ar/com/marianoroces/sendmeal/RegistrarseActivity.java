package ar.com.marianoroces.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import ar.com.marianoroces.sendmeal.model.CuentaBancaria;
import ar.com.marianoroces.sendmeal.model.Tarjeta;
import ar.com.marianoroces.sendmeal.model.Usuario;

public class RegistrarseActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtApellido;
    EditText txtMail;
    EditText txtContrasenia;
    EditText txtRepetirContrasenia;
    EditText txtNroTarjeta;
    EditText txtCCVTarjeta;
    EditText txtCBU;
    EditText txtAliasCBU;
    EditText txtCargaInicial;
    Spinner spMesVencimientoTarjeta;
    Spinner spAnioVencimientoTarjeta;
    RadioGroup rgTipoTarjeta;
    RadioButton rbDebito;
    RadioButton rbCredito;
    Switch swCargaInicial;
    SeekBar sbCargaInicial;
    CheckBox cbTerminosYCondiciones;
    Button btnRegistrar;
    String mesSeleccionado;
    String anioSeleccionado;
    Toolbar toolbarRegistrarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtMail = findViewById(R.id.txtMail);
        txtContrasenia = findViewById(R.id.txtContraseña);
        txtRepetirContrasenia = findViewById(R.id.txtRepetirContraseña);
        txtNroTarjeta = findViewById(R.id.txtNroTarjeta);
        txtCCVTarjeta = findViewById(R.id.txtCCVTarjeta);
        spMesVencimientoTarjeta = findViewById(R.id.spMesVencimientoTarjeta);
        spAnioVencimientoTarjeta = findViewById(R.id.spAnioVencimientoTarjeta);
        txtCBU = findViewById(R.id.txtCBU);
        txtAliasCBU = findViewById(R.id.txtAliasCBU);
        txtCargaInicial = findViewById(R.id.txtCargaInicial);
        rgTipoTarjeta = findViewById(R.id.rgTipoTarjeta);
        rbDebito = findViewById(R.id.rbDebito);
        rbCredito = findViewById(R.id.rbCredito);
        swCargaInicial = findViewById(R.id.switchCargaInicial);
        sbCargaInicial = findViewById(R.id.sbCargaInicial);
        cbTerminosYCondiciones = findViewById(R.id.cbTerminosYCondiciones);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        toolbarRegistrarme = findViewById(R.id.tbRegistrarme);
        setSupportActionBar(toolbarRegistrarme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] meses = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String[] anios = {"2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};

        ArrayAdapter<String> adapterMeses = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, meses);
        ArrayAdapter<String> adapterAnios = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, anios);

        //---------------------------------------------------------------------------------------------------------------------------------//

        spMesVencimientoTarjeta.setAdapter(adapterMeses);
        spAnioVencimientoTarjeta.setAdapter(adapterAnios);

        spMesVencimientoTarjeta.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mesSeleccionado = spMesVencimientoTarjeta.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spAnioVencimientoTarjeta.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                anioSeleccionado = spAnioVencimientoTarjeta.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CompoundButton.OnCheckedChangeListener listenerCB = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                switch(compoundButton.getId()){
                    case R.id.cbTerminosYCondiciones:
                        if(isChecked) {
                            btnRegistrar.setEnabled(true);
                        } else {
                            btnRegistrar.setEnabled(false);
                        }
                        break;
                }

            }
        };

        cbTerminosYCondiciones.setOnCheckedChangeListener(listenerCB);

        swCargaInicial.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                switch(compoundButton.getId()) {
                    case R.id.switchCargaInicial:
                        if(isChecked) {
                            sbCargaInicial.setVisibility(View.VISIBLE);
                            txtCargaInicial.setVisibility(View.VISIBLE);
                        } else {
                            sbCargaInicial.setVisibility(View.GONE);
                            txtCargaInicial.setVisibility(View.GONE);
                        }
                        break;
                }

            }

        });

        sbCargaInicial.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                txtCargaInicial.setText("$"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        btnRegistrar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {

                switch(validarDatos(txtMail.getText().toString(),
                                    txtContrasenia.getText().toString(),
                                    txtRepetirContrasenia.getText().toString(),
                                    rbDebito.isChecked(),
                                    rbCredito.isChecked(),
                                    txtNroTarjeta.getText().toString(),
                                    txtCCVTarjeta.getText().toString(),
                                    mesSeleccionado,
                                    anioSeleccionado)){
                    case "mail":
                        txtMail.setError("INGRESAR E-MAIL");
                        txtMail.requestFocus();
                        break;
                    case "contrasenia":
                        txtContrasenia.setError("INGRESAR CONTRASEÑA");
                        txtContrasenia.requestFocus();
                        break;
                    case "contraseniaRepetida":
                        txtRepetirContrasenia.setError("REPETIR CONTRASEÑA");
                        txtRepetirContrasenia.requestFocus();
                        break;
                    case "contraseniasNoCoinciden":
                        txtRepetirContrasenia.setError("CONTRASEÑAS NO COINCIDEN");
                        txtRepetirContrasenia.requestFocus();
                        break;
                    case "debitoCredito":
                        rbCredito.setError("SELECCIONAR TIPO DE TARJETA");
                        rbDebito.setError("SELECCIONAR TIPO DE TARJETA");
                        rbCredito.requestFocus();
                        break;
                    case "nroTarjeta":
                        txtNroTarjeta.setError("NUMERO DE TARJETA INCORRECTO");
                        txtNroTarjeta.requestFocus();
                        break;
                    case "ccvTarjeta":
                        txtCCVTarjeta.setError("CODIGO CCV INCORRECTO");
                        txtCCVTarjeta.requestFocus();
                        break;
                    case "vencimientoTarjeta":
                        ((TextView) spMesVencimientoTarjeta.getSelectedView()).setError("MES DE VENCIMIENTO INCORRECTO");
                        spMesVencimientoTarjeta.requestFocus();
                        break;
                    case "exito":

                        Date vencimientoTarjeta = new Date();
                        vencimientoTarjeta.setYear(Integer.parseInt(anioSeleccionado));
                        vencimientoTarjeta.setMonth(Integer.parseInt(mesSeleccionado));

                        boolean esCredito = false;
                        if(rbCredito.isChecked()) esCredito = true;

                        String nombreCompleto = txtApellido.getText().toString()+", "+txtNombre.getText().toString();

                        Tarjeta tarjeta = new Tarjeta(txtNroTarjeta.getText().toString(), txtCCVTarjeta.getText().toString(), vencimientoTarjeta, esCredito);
                        CuentaBancaria cuenta = new CuentaBancaria(txtCBU.getText().toString(), txtAliasCBU.getText().toString());
                        Usuario user = new Usuario(1,
                                                    nombreCompleto,
                                                    txtContrasenia.getText().toString(),
                                                    txtMail.getText().toString(),
                                                    Double.parseDouble(txtCargaInicial.getText().toString().substring(1)),
                                                    tarjeta,
                                                    cuenta);

                        txtNombre.setText("");
                        txtApellido.setText("");
                        txtMail.setText("");
                        txtContrasenia.setText("");
                        txtRepetirContrasenia.setText("");
                        txtNroTarjeta.setText("");
                        txtCCVTarjeta.setText("");
                        txtCBU.setText("");
                        txtAliasCBU.setText("");
                        rbDebito.setChecked(false);
                        rbCredito.setChecked(false);
                        cbTerminosYCondiciones.setChecked(false);
                        swCargaInicial.setChecked(false);
                        spMesVencimientoTarjeta.setSelection(0);
                        spAnioVencimientoTarjeta.setSelection(0);
                        sbCargaInicial.setProgress(0);

                        txtNombre.requestFocus();

                        Toast.makeText(RegistrarseActivity.this, "REGISTRO COMPLETADO", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private String validarDatos (String mail,
                                 String contrasenia,
                                 String contraseniaRepetida,
                                 boolean debito,
                                 boolean credito,
                                 String nroTarjeta,
                                 String ccvTarjeta,
                                 String mesVencimiento,
                                 String anioVencimiento){

        if (!validEmail(mail)){
            return "mail";
        } else {
            if(contrasenia.equals("")) {
                return "contrasenia";
            } else {
                if(contraseniaRepetida.equals("")) {
                    return "contraseniaRepetida";
                } else {
                    if(!contrasenia.equals(contraseniaRepetida)) {
                        return "contraseniasNoCoinciden";
                    } else {
                        if (!credito && !debito) {
                            return "debitoCredito";
                        } else {
                            if(!nroTarjeta.matches("^[0-9]{16}$")) {
                                return "nroTarjeta";
                            } else {
                                if(!ccvTarjeta.matches("^[0-9]{3}|[0-9]{4}$")) {
                                    return "ccvTarjeta";
                                } else {
                                    if(!compararVencimientoTarjetaCredito(mesVencimiento, anioVencimiento)) {
                                        return "vencimientoTarjeta";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "exito";
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean compararVencimientoTarjetaCredito(String mesVencimiento, String anioVencimiento) {
        Calendar fechaHoy = Calendar.getInstance();
        int mes = Integer.parseInt(mesVencimiento);
        int anio = Integer.parseInt(anioVencimiento);

        if (fechaHoy.get(Calendar.YEAR) == anio) {
            if ((mes - fechaHoy.get(Calendar.MONTH)) <= 3) {
                return false;
            } else {
                return true;
            }
        } else {
            if ((anio - fechaHoy.get(Calendar.YEAR)) < 0) {
                return false;
            } else {
                switch(anio - fechaHoy.get(Calendar.YEAR)) {
                    case 1:
                        if(((mes+12) - fechaHoy.get(Calendar.MONTH)) <= 3) {
                            return false;
                        } else {
                            return true;
                        }
                    default:
                        return true;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registrarse_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuVolverRegistrarse) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}