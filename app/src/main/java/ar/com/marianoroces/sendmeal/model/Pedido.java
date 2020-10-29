package ar.com.marianoroces.sendmeal.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import ar.com.marianoroces.sendmeal.enums.TipoEnvio;

@Entity
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_pedido")
    public int id;

    //ENVIO, TAKE_AWAY
    @ColumnInfo(name = "tipo_envio")
    TipoEnvio tipoEnvio;

    Date fecha;

    @Ignore
    List<PedidoPlato> platos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
