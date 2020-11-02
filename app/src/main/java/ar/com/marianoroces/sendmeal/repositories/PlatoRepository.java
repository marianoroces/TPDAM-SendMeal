package ar.com.marianoroces.sendmeal.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.com.marianoroces.sendmeal.DAO.PlatoDAO;
import ar.com.marianoroces.sendmeal.activities.NuevoPlatoActivity;
import ar.com.marianoroces.sendmeal.services.MyRetrofit;
import ar.com.marianoroces.sendmeal.services.PlatoService;
import ar.com.marianoroces.sendmeal.utils.OnPlatoResultCallback;
import ar.com.marianoroces.sendmeal.model.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatoRepository implements OnPlatoResultCallback {

    private PlatoDAO platoDao;
    private OnPlatoResultCallback callback;
    private PlatoService platoService;
    private List<Plato> listaPlatos = new ArrayList<>();

    public PlatoRepository(Application application, OnPlatoResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        platoDao = db.platoDao();
        callback = context;
        platoService = MyRetrofit.getInstance().crearPlatoService();
    }

    public void insertar(final Plato plato){
        Log.d("DEBUG", "Plato insertado con Room");
        platoDao.insertar(plato);
    }

    public void insertarRest(final Plato plato) {
        JsonObject aux = new JsonObject();

        aux.addProperty("id", plato.getId());
        aux.addProperty("titulo", plato.getTitulo());
        aux.addProperty("descripcion", plato.getDescripcion());
        aux.addProperty("precio", plato.getPrecio());
        aux.addProperty("calorias", plato.getCalorias());

        Call<Plato> callPlato = platoService.crearPlato(aux);

        callPlato.enqueue(
                new Callback<Plato>() {
                    @Override
                    public void onResponse(Call<Plato> call, Response<Plato> response) {
                        Log.d("DEBUG", "Plato insertado con Retrofit");
                    }

                    @Override
                    public void onFailure(Call<Plato> call, Throwable t) {
                        Log.d("DEBUG", "FALLO AL INSERTAR PLATO CON RETROFIT");
                    }
                }
        );
    }

    public void borrar(final Plato plato){
        platoDao.borrar(plato);
    }

    public void actualizar(final Plato plato){
        platoDao.actualizar(plato);
    }

    public void buscarPorId(String id){
        new BuscarPlatoById(platoDao, this).execute(id);
    }

    public void buscarTodos(){
        new BuscarPlatos(platoDao, this).execute();
    }

    public void buscarTodosRest(){
        Call<List<Plato>> callPlatos = platoService.buscarTodos();

        callPlatos.enqueue(
                new Callback<List<Plato>>() {
                    @Override
                    public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                        if(response.code() == 200) {
                            Log.d("DEBUG", "Return exitoso");
                            Log.d("DEBUG", response.body().toString());
                            listaPlatos.clear();
                            listaPlatos.addAll(response.body());
                            callback.onResult(listaPlatos);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plato>> call, Throwable t) {
                        Log.d("DEBUG", "Return fallido");
                        Log.d("DEBUG", t.getMessage());
                    }
                }
        );
    }

    @Override
    public void onResult(List<Plato> platos){
        Log.d("DEBUG", "Platos cargados");
        callback.onResult(platos);
    }

    @Override
    public void onResult(Plato plato) {
        callback.onResult(plato);
    }

    class BuscarPlatos extends AsyncTask<String, Void, List<Plato>> {

        private PlatoDAO dao;
        private OnPlatoResultCallback callback;

        public BuscarPlatos(PlatoDAO dao, OnPlatoResultCallback context) {
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected List<Plato> doInBackground(String... strings) {
            List<Plato> platos = dao.buscarTodos();
            return platos;
        }

        @Override
        protected void onPostExecute(List<Plato> platos) {
            super.onPostExecute(platos);
            callback.onResult(platos);
        }
    }

    class BuscarPlatoById extends AsyncTask<String, Void, Plato> {

        private PlatoDAO dao;
        private OnPlatoResultCallback callback;

        public BuscarPlatoById(PlatoDAO dao, OnPlatoResultCallback context){
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected Plato doInBackground(String... strings) {
            Plato plato = dao.buscar(strings[0]);
            return plato;
        }

        @Override
        protected void onPostExecute(Plato plato){
            super.onPostExecute(plato);
            callback.onResult(plato);
        }
    }

    public List<Plato> getListaPlatos(){
        return listaPlatos;
    }
}
