package lanchonete_design_patterns_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CriarPedidoRequest(
        @NotBlank String cliente,
        @NotEmpty List<@Valid ItemRequest> itens,
        @Valid FormaPagamentoRequest formaPagamento
) {
}
