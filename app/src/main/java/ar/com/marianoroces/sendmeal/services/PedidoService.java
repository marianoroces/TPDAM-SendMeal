package ar.com.marianoroces.sendmeal.services;

import com.google.gson.JsonObject;

import java.util.List;

import ar.com.marianoroces.sendmeal.model.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoService {

    @GET("pedidos")
    Call<List<Pedido>> buscarTodos();

    @GET("pedidos/{id}")
    Call<Pedido> getPedido(@Path("id") String id);

    @DELETE("pedidos/{id}")
    Call<Pedido> borrar(@Path("id") String id);

    @POST("pedidos")
    Call<Pedido> crearPedido(@Body JsonObject body);

}
