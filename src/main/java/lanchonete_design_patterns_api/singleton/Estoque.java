package lanchonete_design_patterns_api.singleton;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Estoque {
    private final Map<String, Integer> quantidades = new ConcurrentHashMap<>();

    @PostConstruct
    public void inicializar() {
        quantidades.put("X-Burguer", 10);
        quantidades.put("X-Salada", 10);
        quantidades.put("Refrigerante", 20);
        quantidades.put("Suco Natural", 15);
        quantidades.put("Sorvete", 8);
    }

    public boolean temDisponibilidade(String nomeProduto) {
        return quantidades.getOrDefault(nomeProduto, 0) > 0;
    }

    public void baixarEstoque(String nomeProduto) {
        quantidades.computeIfPresent(nomeProduto, (chave, valor) -> Math.max(0, valor - 1));
    }

    public int getQuantidade(String nomeProduto) {
        return quantidades.getOrDefault(nomeProduto, 0);
    }

    public Map<String, Integer> getTodos() {
        return new HashMap<>(quantidades);
    }
}
