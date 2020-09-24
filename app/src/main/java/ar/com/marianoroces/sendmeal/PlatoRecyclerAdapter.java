package ar.com.marianoroces.sendmeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.marianoroces.sendmeal.model.Plato;

public class PlatoRecyclerAdapter extends RecyclerView.Adapter<PlatoViewHolder> {

    private List<Plato> listaPlatos;

    public PlatoRecyclerAdapter(List<Plato> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoViewHolder viewHolder = new PlatoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder platoHolder, int position) {
        Plato platoAuxiliar = listaPlatos.get(position);

        platoHolder.imgPlato.setImageResource(R.drawable.food_background);
        platoHolder.txtTituloPlato.setText(platoAuxiliar.getTitulo());
        platoHolder.txtMontoPlato.setText("$"+String.valueOf(platoAuxiliar.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }
}
