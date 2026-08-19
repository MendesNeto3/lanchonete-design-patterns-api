package lanchonete_design_patterns_api.observer;

import lanchonete_design_patterns_api.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class CozinhaNotificador implements StatusPedidoObserver {
    @Override
    public void atualizar(Pedido pedido) {
        System.out.printf("[Painel da Cozinha] Pedido #%s mudou para: %s%n",
                pedido.getId(), pedido.getStatus());
    }
}
