package lanchonete_design_patterns_api.adapter;

public interface ProcessadorPagamento {
    boolean processar(String numeroCartao, double valor);
}
