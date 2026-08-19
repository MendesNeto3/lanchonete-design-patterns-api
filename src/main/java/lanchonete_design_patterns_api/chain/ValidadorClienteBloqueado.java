package lanchonete_design_patterns_api.chain;

import lanchonete_design_patterns_api.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidadorClienteBloqueado extends ValidadorPedido{
    private static final Set<String> CLIENTES_BLOQUEADOS = Set.of("cliente_inadimplente");

    @Override
    protected boolean validarRegra(Pedido pedido) {
        if (CLIENTES_BLOQUEADOS.contains(pedido.getCliente())) {
            throw new IllegalStateException("Cliente bloqueado: " + pedido.getCliente());
        }
        return true;
    }
}
