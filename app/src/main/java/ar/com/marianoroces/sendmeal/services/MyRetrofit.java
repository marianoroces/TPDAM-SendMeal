package ar.com.marianoroces.sendmeal.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final MyRetrofit instancia = new MyRetrofit();

    private Retrofit retrofit;

    public static MyRetrofit getInstance(){
        return instancia;
    }

    private MyRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:3001/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public PlatoService crearPlatoService(){
        return retrofit.create(PlatoService.class);
    }

    public PedidoService crearPedidoService() {
        return retrofit.create(PedidoService.class);
    }
}
