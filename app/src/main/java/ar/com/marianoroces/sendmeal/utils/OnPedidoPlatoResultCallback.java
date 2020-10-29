package ar.com.marianoroces.sendmeal.utils;

import java.util.List;

import ar.com.marianoroces.sendmeal.model.PedidoPlato;

public interface OnPedidoPlatoResultCallback {
    void onResult(PedidoPlato pedidoPlato);
    void onResult(List<PedidoPlato> pedidoPlatoes);
}
