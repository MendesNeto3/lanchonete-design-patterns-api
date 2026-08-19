package lanchonete_design_patterns_api.adapter;

import org.springframework.stereotype.Component;

@Component
public class GatewayPagamentoExterno {
    public boolean efetuarCobranca(String numeroCartao, long valorEmCentavos) {
        System.out.printf("[Gateway Externo] Cobrando %d centavos no cartao %s...%n",
                valorEmCentavos, numeroCartao);
        return true;
    }
}
