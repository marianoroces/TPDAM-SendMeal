package ar.com.marianoroces.sendmeal.services;

import androidx.room.Update;

import java.util.List;

import ar.com.marianoroces.sendmeal.model.Plato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlatoService {

    @GET("platos/{id}")
    Call<Plato> getPlato(@Path("id") String id);

    @GET("platos")
    Call<List<Plato>> buscarTodos();

    @DELETE("platos")
    Call<Plato> borrar(@Body String body);

    @POST("platos")
    Call<Plato> crearPlato(@Body String body);
}
