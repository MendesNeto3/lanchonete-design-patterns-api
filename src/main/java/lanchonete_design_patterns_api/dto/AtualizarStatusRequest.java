package lanchonete_design_patterns_api.dto;

import jakarta.validation.constraints.NotNull;
import lanchonete_design_patterns_api.enums.StatusPedido;

public record AtualizarStatusRequest(
        @NotNull StatusPedido status
) {
}
