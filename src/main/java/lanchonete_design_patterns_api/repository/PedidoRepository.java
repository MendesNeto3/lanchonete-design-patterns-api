package lanchonete_design_patterns_api.repository;

import lanchonete_design_patterns_api.model.Pedido;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PedidoRepository {

    private final Map<String, Pedido> pedidos = new ConcurrentHashMap<>();

    public Pedido salvar(Pedido pedido) {
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    public Optional<Pedido> buscarPorId(String id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }
}
