package ar.com.marianoroces.sendmeal.repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ar.com.marianoroces.sendmeal.DAO.PedidoDAO;
import ar.com.marianoroces.sendmeal.DAO.PedidoPlatoDAO;
import ar.com.marianoroces.sendmeal.DAO.PlatoDAO;
import ar.com.marianoroces.sendmeal.model.Pedido;
import ar.com.marianoroces.sendmeal.model.PedidoPlato;
import ar.com.marianoroces.sendmeal.model.Plato;
import ar.com.marianoroces.sendmeal.utils.Converters;

@Database(entities = {Plato.class, Pedido.class, PedidoPlato.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlatoDAO platoDao();
    public abstract PedidoDAO pedidoDAO();
    public abstract PedidoPlatoDAO pedidoPlatoDAO();
    public static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "dam-tp.db")
                    .build();
        }
        return INSTANCE;
    }

}
