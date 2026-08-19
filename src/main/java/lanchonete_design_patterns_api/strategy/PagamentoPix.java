package lanchonete_design_patterns_api.strategy;

public class PagamentoPix implements FormaPagamento {
    @Override
    public boolean pagar(double valor) {
        System.out.printf("Pagamento de R$ %.2f via PIX aprovado instantaneamente.%n", valor);
        return true;    }

    @Override
    public String getDescricao() {
        return "PIX";
    }
}
