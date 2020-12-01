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
    public String idPedido;

    //ENVIO, TAKE_AWAY
    @ColumnInfo(name = "tipo_envio")
    TipoEnvio tipoEnvio;

    Date fecha;
    String email;
    String calle;
    String numero;
    double precio;
    public double ubicacionLat;
    public double ubicacionLng;

    @Ignore
    List<PedidoPlato> platosPedidos;

    public Pedido(TipoEnvio tipoEnvio, Date fecha, String email, String calle, String numero, double precio) {
        this.tipoEnvio = tipoEnvio;
        this.fecha = fecha;
        this.email = email;
        this.calle = calle;
        this.numero = numero;
        this.precio = precio;
        this.idPedido = UUID.randomUUID().toString();
    }

    public Pedido() {
    }

    @NonNull
    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(@NonNull String idPedido) {
        this.idPedido = idPedido;
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

    public List<PedidoPlato> getPlatosPedidos() {
        return platosPedidos;
    }

    public void setPlatosPedidos(List<PedidoPlato> platosPedidos) {
        this.platosPedidos = platosPedidos;
    }

    public double getUbicacionLat() {
        return ubicacionLat;
    }

    public void setUbicacionLat(double ubicacionLat) {
        this.ubicacionLat = ubicacionLat;
    }

    public double getUbicacionLng() {
        return ubicacionLng;
    }

    public void setUbicacionLng(double ubicacionLng) {
        this.ubicacionLng = ubicacionLng;
    }
}
