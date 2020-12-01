package ar.com.marianoroces.sendmeal.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ar.com.marianoroces.sendmeal.DAO.PedidoDAO;
import ar.com.marianoroces.sendmeal.model.PedidoPlato;
import ar.com.marianoroces.sendmeal.services.MyRetrofit;
import ar.com.marianoroces.sendmeal.services.PedidoService;
import ar.com.marianoroces.sendmeal.utils.OnPedidoResultCallback;
import ar.com.marianoroces.sendmeal.model.Pedido;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepository implements OnPedidoResultCallback{

    private PedidoDAO pedidoDao;
    private OnPedidoResultCallback callback;
    private PedidoService pedidoService;

    public PedidoRepository(Application application, OnPedidoResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        pedidoDao = db.pedidoDAO();
        callback = context;
        pedidoService = MyRetrofit.getInstance().crearPedidoService();
    }

    public Long insertar(final Pedido pedido){
        Long idAux = pedidoDao.insertar(pedido);
        Log.d("DEBUG", "Pedido insertado: "+String.valueOf(idAux));
        return idAux;
    }

    public void insertarRest(final Pedido pedido){
        JsonArray idPlatos = new JsonArray();
        for(PedidoPlato pedidoPlatoAux : pedido.getPlatosPedidos()){
            idPlatos.add(pedidoPlatoAux.plato.getId());
        }

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fechaPedido = format.format(pedido.getFecha());

        JsonObject pedidoAux = new JsonObject();

        Log.d("DEBUG", idPlatos.toString());

        pedidoAux.addProperty("id", pedido.getIdPedido());
        pedidoAux.add("platosId", idPlatos);
        pedidoAux.addProperty("e-mail", pedido.getEmail());
        pedidoAux.addProperty("calle", pedido.getCalle());
        pedidoAux.addProperty("numero", pedido.getNumero());
        pedidoAux.addProperty("fecha", fechaPedido);
        pedidoAux.addProperty("tipo_envio", pedido.getTipoEnvio().toString());
        pedidoAux.addProperty("precio", pedido.getPrecio());
        pedidoAux.addProperty("latitud", pedido.getUbicacionLat());
        pedidoAux.addProperty("longitud", pedido.getUbicacionLng());

        Call<Pedido> callPedido = pedidoService.crearPedido(pedidoAux);

        callPedido.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                Log.d("DEBUG", "Pedido insertado con Retrofit");
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());
                Log.d("DEBUG", "FALLO AL INSERTAR PEDIDO CON RETROFIT");
            }
        });
    }

    public void borrar(final Pedido pedido){
        pedidoDao.borrar(pedido);
    }

    public void actualizar(final Pedido pedido){
        pedidoDao.actualizar(pedido);
    }

    public void buscarPorId(String id){
        new BuscarPedidoById(pedidoDao, this).execute(id);
    }

    @Override
    public void onResult(Pedido pedido) {
        callback.onResult(pedido);
    }

    class BuscarPedidoById extends AsyncTask<String, Void, Pedido> {

        private PedidoDAO dao;
        private OnPedidoResultCallback callback;

        public BuscarPedidoById(PedidoDAO dao, OnPedidoResultCallback context){
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected Pedido doInBackground(String... strings) {
            Pedido pedido = dao.buscar(strings[0]);
            return pedido;
        }

        @Override
        protected void onPostExecute(Pedido pedido){
            super.onPostExecute(pedido);
            callback.onResult(pedido);
        }
    }
}
