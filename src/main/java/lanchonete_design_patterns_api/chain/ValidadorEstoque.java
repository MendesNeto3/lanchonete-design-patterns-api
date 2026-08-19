package lanchonete_design_patterns_api.chain;

import lanchonete_design_patterns_api.model.Pedido;
import lanchonete_design_patterns_api.model.Produto;
import lanchonete_design_patterns_api.singleton.Estoque;
import org.springframework.stereotype.Component;

@Component
public class ValidadorEstoque extends ValidadorPedido {
    private final Estoque estoque;

    public ValidadorEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    @Override
    protected boolean validarRegra(Pedido pedido) {
        for (Produto produto : pedido.getItens()) {
            if (!estoque.temDisponibilidade(produto.getNome())) {
                throw new IllegalStateException("Produto sem estoque: " + produto.getNome());
            }
        }
        return true;
    }
}
