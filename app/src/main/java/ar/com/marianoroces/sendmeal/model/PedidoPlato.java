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
                    onDelete = ForeignKey.CASCADE)})
public class PedidoPlato {

    @PrimaryKey(autoGenerate = true)
    public int idPedidoPlato;

    @ColumnInfo(name = "id_pedido")
    public Long idPedido;

    @Embedded
    public Plato plato;

    public int getIdPedidoPlato() {
        return idPedidoPlato;
    }

    public void setIdPedidoPlato(int idPedidoPlato) {
        this.idPedidoPlato = idPedidoPlato;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }
}
