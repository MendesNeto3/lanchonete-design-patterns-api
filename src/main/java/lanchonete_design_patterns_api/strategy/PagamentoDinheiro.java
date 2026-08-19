package lanchonete_design_patterns_api.strategy;

public class PagamentoDinheiro implements FormaPagamento {
    private final double valorRecebido;

    public PagamentoDinheiro(double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    @Override
    public boolean pagar(double valor) {
        if (valorRecebido < valor) {
            System.out.println("Valor recebido insuficiente para pagamento em dinheiro.");
            return false;
        }
        double troco = valorRecebido - valor;
        System.out.printf("Pagamento de R$ %.2f em dinheiro aprovado. Troco: R$ %.2f%n", valor, troco);
        return true;
    }

    @Override
    public String getDescricao() {
        return "Dinheiro";
    }
}
