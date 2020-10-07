package ar.com.marianoroces.sendmeal.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.marianoroces.sendmeal.R;
import ar.com.marianoroces.sendmeal.model.Plato;

public class PlatoRecyclerAdapter extends RecyclerView.Adapter<PlatoViewHolder> {

    private List<Plato> listaPlatos;
    private AppCompatActivity activity;
    private boolean activar;

    public PlatoRecyclerAdapter(List<Plato> listaPlatos, AppCompatActivity act) {
        this.listaPlatos = listaPlatos;
        this.activity = act;
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoViewHolder viewHolder = new PlatoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlatoViewHolder platoHolder, int position) {
        final Plato platoAuxiliar = listaPlatos.get(position);

        platoHolder.imgPlato.setImageResource(R.drawable.food_background);
        platoHolder.txtTituloPlato.setText(platoAuxiliar.getTitulo());
        platoHolder.txtMontoPlato.setText("$"+String.valueOf(platoAuxiliar.getPrecio()));

        if(activar) {
            platoHolder.btnPedir.setVisibility(View.VISIBLE);
        } else {
            platoHolder.btnPedir.setVisibility(View.INVISIBLE);
        }

        platoHolder.getBtnPedir().setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPedido = new Intent();
                intentPedido.putExtra("nombrePlato", platoAuxiliar.getTitulo());
                intentPedido.putExtra("precioPlato", String.valueOf(platoAuxiliar.getPrecio()));
                intentPedido.putExtra("descripcionPlato", platoAuxiliar.getDescripcion());
                intentPedido.putExtra("caloriasPlato", String.valueOf(platoAuxiliar.getCalorias()));
                activity.setResult(Activity.RESULT_OK, intentPedido);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public void activarBotones(boolean activar){
        this.activar = activar;
        notifyDataSetChanged();
    }
}
