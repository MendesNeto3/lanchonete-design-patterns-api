package lanchonete_design_patterns_api.builder;

import lanchonete_design_patterns_api.model.Pedido;
import lanchonete_design_patterns_api.model.Produto;
import lanchonete_design_patterns_api.strategy.FormaPagamento;

import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {
    private String cliente;
    private final List<Produto> itens = new ArrayList<>();
    private FormaPagamento formaPagamento;

    public PedidoBuilder paraCliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public PedidoBuilder adicionarItem(Produto produto) {
        this.itens.add(produto);
        return this;
    }

    public PedidoBuilder comFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public Pedido build() {
        if (cliente == null || cliente.isBlank()) {
            throw new IllegalStateException("Pedido precisa de um cliente.");
        }
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido precisa de ao menos um item.");
        }
        return new Pedido(cliente, itens, formaPagamento);
    }
}
