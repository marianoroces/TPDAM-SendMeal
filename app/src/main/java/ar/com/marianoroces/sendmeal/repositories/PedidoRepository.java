package ar.com.marianoroces.sendmeal.repositories;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import ar.com.marianoroces.sendmeal.DAO.PedidoDAO;
import ar.com.marianoroces.sendmeal.utils.OnPedidoResultCallback;
import ar.com.marianoroces.sendmeal.model.Pedido;

public class PedidoRepository implements OnPedidoResultCallback{

    private PedidoDAO pedidoDao;
    private OnPedidoResultCallback callback;

    public PedidoRepository(Application application, OnPedidoResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        pedidoDao = db.pedidoDAO();
        callback = context;
    }

    public void insertar(final Pedido pedido){
        pedidoDao.insertar(pedido);
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

    public void buscarTodos(){
        new BuscarPedidos(pedidoDao, this).execute();
    }

    @Override
    public void onResult(Pedido pedido) {
        callback.onResult(pedido);
    }

    @Override
    public void onResult(List<Pedido> pedidos) {
        callback.onResult(pedidos);
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

    class BuscarPedidos extends AsyncTask<String, Void, List<Pedido>> {

        private PedidoDAO dao;
        private OnPedidoResultCallback callback;

        public BuscarPedidos(PedidoDAO dao, OnPedidoResultCallback context) {
            this.dao = dao;
            this.callback = context;
        }

        @Override
        protected List<Pedido> doInBackground(String... strings) {
            List<Pedido> pedidos = dao.buscarTodos();
            return pedidos;
        }

        @Override
        protected void onPostExecute(List<Pedido> pedidos) {
            super.onPostExecute(pedidos);
            callback.onResult(pedidos);
        }
    }
}
