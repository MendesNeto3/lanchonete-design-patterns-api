package lanchonete_design_patterns_api.strategy;

public interface FormaPagamento {
    boolean pagar(double valor);
    String getDescricao();
}
