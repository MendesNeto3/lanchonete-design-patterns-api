package lanchonete_design_patterns_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lanchonete_design_patterns_api.enums.TipoAdicional;
import lanchonete_design_patterns_api.enums.TipoProduto;

import java.util.List;

public record ItemRequest(
        @NotNull TipoProduto tipo,
        @NotBlank String nome,
        @Positive double preco,
        List<TipoAdicional> adicionais
) {
}
