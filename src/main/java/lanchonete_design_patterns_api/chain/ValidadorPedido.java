package lanchonete_design_patterns_api.chain;

import lanchonete_design_patterns_api.model.Pedido;

public abstract class ValidadorPedido {
    private ValidadorPedido proximo;

    public ValidadorPedido setProximo(ValidadorPedido proximo) {
        this.proximo = proximo;
        return proximo;
    }

    public void validar(Pedido pedido) {
        if (validarRegra(pedido) && proximo != null) {
            proximo.validar(pedido);
        }
    }

    protected abstract boolean validarRegra(Pedido pedido);
}
