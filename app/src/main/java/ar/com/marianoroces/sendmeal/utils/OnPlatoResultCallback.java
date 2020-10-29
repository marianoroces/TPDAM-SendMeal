package ar.com.marianoroces.sendmeal.utils;

import java.util.List;

import ar.com.marianoroces.sendmeal.model.Plato;

public interface OnPlatoResultCallback {
    void onResult(List<Plato> plato);
    void onResult(Plato plato);
}
