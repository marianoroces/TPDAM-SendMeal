package ar.com.marianoroces.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtApellido;
    EditText txtMail;
    EditText txtContrasenia;
    EditText txtRepetirContrasenia;
    EditText txtNroTarjeta;
    EditText txtCCVTarjeta;
    EditText txtMesVencimientoTarjeta;
    EditText txtAnioVencimientoTarjeta;
    EditText txtCBU;
    EditText txtAliasCBU;
    EditText txtCargaInicial;
    RadioGroup rgTipoTarjeta;
    RadioButton rbDebito;
    RadioButton rbCredito;
    Switch swCargaInicial;
    SeekBar sbCargaInicial;
    CheckBox cbTerminosYCondiciones;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtMail = findViewById(R.id.txtMail);
        txtContrasenia = findViewById(R.id.txtContraseña);
        txtRepetirContrasenia = findViewById(R.id.txtRepetirContraseña);
        txtNroTarjeta = findViewById(R.id.txtNroTarjeta);
        txtCCVTarjeta = findViewById(R.id.txtCCVTarjeta);
        txtMesVencimientoTarjeta = findViewById(R.id.txtMesVencimientoTarjeta);
        txtAnioVencimientoTarjeta = findViewById(R.id.txtAnioVencimientoTarjeta);
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

        //------------------------------------------------------------------------------------------------------//


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
                                    txtMesVencimientoTarjeta.getText().toString(),
                                    txtAnioVencimientoTarjeta.getText().toString())){
                    case "mail":
                        Toast.makeText(MainActivity.this, "FALTA INGRESAR E-MAIL", Toast.LENGTH_LONG).show();
                        break;
                    case "contrasenia":
                        Toast.makeText(MainActivity.this, "FALTA INGRESAR CONTRASEÑA", Toast.LENGTH_LONG).show();
                        break;
                    case "contraseniaRepetida":
                        Toast.makeText(MainActivity.this, "FALTA REPETIR CONTRASEÑA", Toast.LENGTH_LONG).show();
                        break;
                    case "contraseniasNoCoinciden":
                        Toast.makeText(MainActivity.this, "LAS CONTRASEÑAS NO COINCIDEN", Toast.LENGTH_LONG).show();
                        break;
                    case "debitoCredito":
                        Toast.makeText(MainActivity.this, "FALTA SELECCIONAR TIPO DE TARJETA", Toast.LENGTH_LONG).show();
                        break;
                    case "nroTarjeta":
                        Toast.makeText(MainActivity.this, "NUMERO DE TARJETA INCORRECTO", Toast.LENGTH_LONG).show();
                        break;
                    case "ccvTarjeta":
                        Toast.makeText(MainActivity.this, "CODIGO CCV INCORRECTO", Toast.LENGTH_LONG).show();
                        break;
                    case "vencimientoTarjeta":
                        Toast.makeText(MainActivity.this, "MES DE VENCIMIENTO INCORRECTO", Toast.LENGTH_LONG).show();
                        break;
                    case "exito":
                        Toast.makeText(MainActivity.this, "REGISTRO COMPLETADO", Toast.LENGTH_LONG).show();
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
}