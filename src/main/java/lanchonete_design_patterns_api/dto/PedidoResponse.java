package lanchonete_design_patterns_api.dto;

import lanchonete_design_patterns_api.enums.StatusPedido;

import java.util.List;

public record PedidoResponse (
        String id,
        String cliente,
        List<String> itens,
        double total,
        StatusPedido status,
        String formaPagamento,
        boolean pago
) {
}
