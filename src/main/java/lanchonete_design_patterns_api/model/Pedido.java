package lanchonete_design_patterns_api.model;

import lanchonete_design_patterns_api.enums.StatusPedido;
import lanchonete_design_patterns_api.strategy.FormaPagamento;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private final String id;
    private final String cliente;
    private final List<Produto> itens;
    private StatusPedido status;
    private FormaPagamento formaPagamento;
    private boolean pago;

    public Pedido(String cliente, List<Produto> itens, FormaPagamento formaPagamento) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.cliente = cliente;
        this.itens = itens != null ? itens : new ArrayList<>();
        this.formaPagamento = formaPagamento;
        this.status = StatusPedido.RECEBIDO;
        this.pago = false;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public List<Produto> getItens() {
        return itens;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public double getValorTotal() {
        double total = 0;
        for (Produto p : itens) {
            total += p.getPreco();
        }
        return total;
    }
}