package lanchonete_design_patterns_api.strategy;

import lanchonete_design_patterns_api.adapter.ProcessadorPagamento;

public class PagamentoCartao implements FormaPagamento {
    private final String numeroCartao;
    private final int parcelas;
    private final ProcessadorPagamento processadorPagamento;

    public PagamentoCartao(String numeroCartao, int parcelas, ProcessadorPagamento processadorPagamento) {
        this.numeroCartao = numeroCartao;
        this.parcelas = parcelas;
        this.processadorPagamento = processadorPagamento;
    }

    @Override
    public boolean pagar(double valor) {
        boolean aprovado = processadorPagamento.processar(numeroCartao, valor);
        if (aprovado) {
            String finalCartao = numeroCartao.substring(numeroCartao.length() - 4);
            System.out.printf("Pagamento de R$ %.2f no cartao final %s em %dx aprovado.%n",
                    valor, finalCartao, parcelas);
        }
        return aprovado;
    }

    @Override
    public String getDescricao() {
        return "Cartao de Credito";
    }
}
