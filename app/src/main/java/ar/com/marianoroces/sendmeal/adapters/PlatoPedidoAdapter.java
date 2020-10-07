package ar.com.marianoroces.sendmeal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;

public class PlatoPedidoAdapter extends ArrayAdapter<Plato> {
    public PlatoPedidoAdapter(Context context, ArrayList<Plato> platos){
        super(context, 0, platos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Plato platoAux = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fila_pedido, parent, false);
        }

        TextView titulo = convertView.findViewById(R.id.txtTituloFilaPedido);
        TextView precio = convertView.findViewById(R.id.txtPrecioFilaPedido);
        TextView descripcion = convertView.findViewById(R.id.txtDescripcionFilaPedido);
        TextView calorias = convertView.findViewById(R.id.txtCaloriasFilaPedido);

        titulo.setText(platoAux.getTitulo());
        precio.setText("$"+String.valueOf(platoAux.getPrecio()));
        descripcion.setText(platoAux.getDescripcion());
        calorias.setText("Calorias: "+String.valueOf(platoAux.getCalorias()));

        return convertView;
    }
}
