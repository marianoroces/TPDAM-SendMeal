package ar.com.marianoroces.sendmeal.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ar.com.marianoroces.sendmeal.DAO.PedidoPlatoDAO;
import ar.com.marianoroces.sendmeal.utils.OnPedidoPlatoResultCallback;
import ar.com.marianoroces.sendmeal.model.PedidoPlato;

public class PedidoPlatoRepository implements OnPedidoPlatoResultCallback {

    PedidoPlatoDAO pedidoPlatoDao;
    OnPedidoPlatoResultCallback callback;

    public PedidoPlatoRepository(Application application, OnPedidoPlatoResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        pedidoPlatoDao = db.pedidoPlatoDAO();
        callback = context;
    }

    public void insertar(final PedidoPlato pedidoPlato){
        pedidoPlatoDao.insertar(pedidoPlato);
        Log.d("DEBUG", "PedidoPlato insertado "+pedidoPlato.getIdPedidoPlato());
    }

    public void borrar(final PedidoPlato pedidoPlato){
        pedidoPlatoDao.borrar(pedidoPlato);
    }

    public void buscarPorId(String id){

    }

    public void buscarTodos(){

    }

    @Override
    public void onResult(PedidoPlato pedidoPlato) {
        callback.onResult(pedidoPlato);
    }

    /*class BuscarPedidoPlatos extends AsyncTask<String, Void, List<PedidoPlato>> {

        private PedidoPlatoDAO dao;
        private OnPedidoPlatoResultCallback callback;

        public BuscarPedidoPlatos(PedidoPlatoDAO dao, OnPedidoPlatoResultCallback context) {
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected List<PedidoPlato> doInBackground(String... strings) {
            List<PedidoPlato> pedidoPlatoes = dao.buscarTodos();
            return pedidoPlatoes;
        }

        @Override
        protected void onPostExecute(List<PedidoPlato> pedidoPlatoes) {
            super.onPostExecute(pedidoPlatoes);
            callback.onResult(pedidoPlatoes);
        }
    }*/

    /*class BuscarPedidoPlatoById extends AsyncTask<String, Void, PedidoPlato> {

        private PedidoPlatoDAO dao;
        private OnPedidoPlatoResultCallback callback;

        public BuscarPedidoPlatoById(PedidoPlatoDAO dao, OnPedidoPlatoResultCallback context){
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected PedidoPlato doInBackground(String... strings) {
            PedidoPlato pedidoPlato = dao.buscar(strings[0]);
            return pedidoPlato;
        }

        @Override
        protected void onPostExecute(PedidoPlato pedidoPlato){
            super.onPostExecute(pedidoPlato);
            callback.onResult(pedidoPlato);
        }
    }*/
}
