package lanchonete_design_patterns_api.decorator;

import lanchonete_design_patterns_api.model.Produto;

public abstract class ProdutoDecorator implements Produto {

    protected final Produto produtoDecorado;

    protected ProdutoDecorator(Produto produtoDecorado) {
        this.produtoDecorado = produtoDecorado;
    }

    @Override
    public String getNome() {
        return produtoDecorado.getNome();
    }
}