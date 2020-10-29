package ar.com.marianoroces.sendmeal.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.com.marianoroces.sendmeal.model.PedidoPlato;

@Dao
public interface PedidoPlatoDAO {

    @Insert
    void insertar(PedidoPlato pedidoPlato);

    @Insert
    void insertarTodos(PedidoPlato... pedidoPlatoes);

    @Delete
    void borrar(PedidoPlato pedidoPlato);

    @Query("SELECT * FROM pedido_plato")
    List<PedidoPlato> buscarTodos();

    @Query("SELECT * FROM pedido_plato WHERE id_item = :id LIMIT 1")
    PedidoPlato buscar(String id);
}
