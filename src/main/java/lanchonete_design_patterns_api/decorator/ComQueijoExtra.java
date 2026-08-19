package lanchonete_design_patterns_api.decorator;

import lanchonete_design_patterns_api.model.Produto;

public class ComQueijoExtra extends ProdutoDecorator {

    private static final double PRECO_ADICIONAL = 3.00;

    public ComQueijoExtra(Produto produtoDecorado) {
        super(produtoDecorado);
    }

    @Override
    public double getPreco() {
        return produtoDecorado.getPreco() + PRECO_ADICIONAL;
    }

    @Override
    public String getDescricao() {
        return produtoDecorado.getDescricao() + " + Queijo Extra";
    }
}
