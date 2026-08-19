package lanchonete_design_patterns_api.dto;

import jakarta.validation.constraints.NotNull;
import lanchonete_design_patterns_api.enums.TipoPagamento;
import lanchonete_design_patterns_api.strategy.FormaPagamento;

public record FormaPagamentoRequest(
        @NotNull TipoPagamento tipo,
        String numeroCartao,
        Integer parcelas,
        Double valorRecebido
) {
}
