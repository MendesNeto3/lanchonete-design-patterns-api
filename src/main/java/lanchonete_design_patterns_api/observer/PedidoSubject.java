package lanchonete_design_patterns_api.observer;

import lanchonete_design_patterns_api.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoSubject {
    private final List<StatusPedidoObserver> observadores;

    public PedidoSubject(List<StatusPedidoObserver> observadores) {
        this.observadores = observadores;
    }

    public void notificarTodos(Pedido pedido) {
        for (StatusPedidoObserver observador : observadores) {
            observador.atualizar(pedido);
        }
    }
}
