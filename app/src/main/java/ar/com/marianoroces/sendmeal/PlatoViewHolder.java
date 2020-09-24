package ar.com.marianoroces.sendmeal;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PlatoViewHolder extends RecyclerView.ViewHolder {

    CardView cvPlato;
    ImageView imgPlato;
    TextView txtTituloPlato, txtPrecioPlato, txtMontoPlato;
    Button btnVer;

    public CardView getCvPlato() {
        return cvPlato;
    }

    public void setCvPlato(CardView cvPlato) {
        this.cvPlato = cvPlato;
    }

    public ImageView getImgPlato() {
        return imgPlato;
    }

    public void setImgPlato(ImageView imgPlato) {
        this.imgPlato = imgPlato;
    }

    public TextView getTxtTituloPlato() {
        return txtTituloPlato;
    }

    public void setTxtTituloPlato(TextView txtTituloPlato) {
        this.txtTituloPlato = txtTituloPlato;
    }

    public TextView getTxtPrecioPlato() {
        return txtPrecioPlato;
    }

    public void setTxtPrecioPlato(TextView txtPrecioPlato) {
        this.txtPrecioPlato = txtPrecioPlato;
    }

    public TextView getTxtMontoPlato() {
        return txtMontoPlato;
    }

    public void setTxtMontoPlato(TextView txtMontoPlato) {
        this.txtMontoPlato = txtMontoPlato;
    }

    public Button getBtnVer() {
        return btnVer;
    }

    public void setBtnVer(Button btnVer) {
        this.btnVer = btnVer;
    }

    public PlatoViewHolder(@NonNull View itemView) {
        super(itemView);
        cvPlato = itemView.findViewById(R.id.cvFilaPlato);
        imgPlato = itemView.findViewById(R.id.imgFilaPlato);
        txtTituloPlato = itemView.findViewById(R.id.txtTituloFilaPlato);
        txtPrecioPlato = itemView.findViewById(R.id.txtPrecioFilaPlato);
        txtMontoPlato = itemView.findViewById(R.id.txtMontoFilaPlato);
        btnVer = itemView.findViewById(R.id.btnFilaPlato);
    }
}
