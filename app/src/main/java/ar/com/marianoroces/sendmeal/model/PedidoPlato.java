package ar.com.marianoroces.sendmeal.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedido_plato",
        foreignKeys = {
        @ForeignKey(entity = Pedido.class,
        parentColumns = "id_pedido",
        childColumns = "id_pedido",
        onDelete = ForeignKey.CASCADE)
})
public class PedidoPlato {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_item")
    public int id;

    @ColumnInfo(name = "id_pedido")
    public String idPedido;

    @Embedded
    Plato plato;

    public PedidoPlato(Plato plato) {
        this.plato = plato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }
}
