package lanchonete_design_patterns_api.observer;

import lanchonete_design_patterns_api.model.Pedido;

public interface StatusPedidoObserver {
    void atualizar(Pedido pedido);
}
