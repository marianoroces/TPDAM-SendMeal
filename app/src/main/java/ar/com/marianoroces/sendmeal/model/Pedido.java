package ar.com.marianoroces.sendmeal.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import ar.com.marianoroces.sendmeal.enums.TipoEnvio;

@Entity
public class Pedido {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_pedido")
    public String id;

    //ENVIO, TAKE_AWAY
    @ColumnInfo(name = "tipo_envio")
    TipoEnvio tipoEnvio;

    Date fecha;
    String email;
    String calle;
    String numero;
    double precio;

    @Ignore
    List<PedidoPlato> platos;

    public Pedido(TipoEnvio tipoEnvio, Date fecha, String email, String calle, String numero, double precio) {
        this.tipoEnvio = tipoEnvio;
        this.fecha = fecha;
        this.email = email;
        this.calle = calle;
        this.numero = numero;
        this.precio = precio;
        this.id = UUID.randomUUID().toString();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<PedidoPlato> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PedidoPlato> platos) {
        this.platos = platos;
    }
}
