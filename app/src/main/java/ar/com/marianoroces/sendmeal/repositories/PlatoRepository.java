package ar.com.marianoroces.sendmeal.repositories;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import ar.com.marianoroces.sendmeal.DAO.PlatoDAO;
import ar.com.marianoroces.sendmeal.utils.OnPlatoResultCallback;
import ar.com.marianoroces.sendmeal.model.Plato;

public class PlatoRepository implements OnPlatoResultCallback {

    private PlatoDAO platoDao;
    private OnPlatoResultCallback callback;

    private PlatoRepository(Application application, OnPlatoResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        platoDao = db.platoDao();
        callback = context;
    }

    public void insertar(final Plato plato){
        platoDao.insertar(plato);
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

    @Override
    public void onResult(List<Plato> platos){
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
}
