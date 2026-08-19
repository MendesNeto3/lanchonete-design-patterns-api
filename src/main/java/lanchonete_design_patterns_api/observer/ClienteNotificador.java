package lanchonete_design_patterns_api.observer;

import lanchonete_design_patterns_api.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class ClienteNotificador implements StatusPedidoObserver {
    @Override
    public void atualizar(Pedido pedido) {
        System.out.printf("[SMS para %s] Seu pedido #%s agora esta: %s%n",
                pedido.getCliente(), pedido.getId(), pedido.getStatus());
    }
}
