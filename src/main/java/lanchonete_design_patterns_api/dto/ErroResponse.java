package lanchonete_design_patterns_api.dto;

public record ErroResponse(
        String mensagem,
        int status
) {
}
