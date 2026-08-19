package lanchonete_design_patterns_api.Factory;

import lanchonete_design_patterns_api.enums.TipoProduto;
import lanchonete_design_patterns_api.model.Produto;

public abstract class ProdutoFactory {
    public abstract Produto criarProduto(String nome, double preco);
    public abstract TipoProduto getTipoSuportado();
}