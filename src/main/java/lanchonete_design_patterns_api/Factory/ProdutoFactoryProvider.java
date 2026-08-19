package lanchonete_design_patterns_api.Factory;

import lanchonete_design_patterns_api.enums.TipoProduto;
import lanchonete_design_patterns_api.model.Produto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProdutoFactoryProvider {
private final Map<TipoProduto, ProdutoFactory> fabricas;

public ProdutoFactoryProvider(List<ProdutoFactory> factories) {
    this.fabricas = factories
            .stream()
            .collect(Collectors.toMap(ProdutoFactory::getTipoSuportado, Function.identity()));
}

public Produto criar(TipoProduto tipo, String nome, double preco) {
    ProdutoFactory factory = fabricas.get(tipo);
    if (factory == null) {
        throw new IllegalArgumentException("Tipo de produto nao suportado: " + tipo);
    }
    return factory.criarProduto(nome, preco);
}
}
