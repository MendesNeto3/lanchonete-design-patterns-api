package lanchonete_design_patterns_api.adapter;

import org.springframework.stereotype.Component;

@Component
public class PagamentoAdapter implements ProcessadorPagamento {
    private final GatewayPagamentoExterno gateway;

    public PagamentoAdapter(GatewayPagamentoExterno gateway) {
        this.gateway = gateway;
    }

    @Override
    public boolean processar(String numeroCartao, double valor) {
        long valorEmCentavos = Math.round(valor * 100);
        return gateway.efetuarCobranca(numeroCartao, valorEmCentavos);
    }
}
