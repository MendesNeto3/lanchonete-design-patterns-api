package lanchonete_design_patterns_api.chain;

import lanchonete_design_patterns_api.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class ValidadorValorMinimo extends ValidadorPedido{
    private static final double VALOR_MINIMO = 5.0;

    @Override
    protected boolean validarRegra(Pedido pedido) {
        if (pedido.getValorTotal() < VALOR_MINIMO) {
            throw new IllegalArgumentException(
                    String.format("Pedido abaixo do valor minimo de R$ %.2f", VALOR_MINIMO));
        }
        return true;
    }
}
