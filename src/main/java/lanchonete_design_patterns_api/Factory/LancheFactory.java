package lanchonete_design_patterns_api.Factory;


import lanchonete_design_patterns_api.enums.TipoProduto;
import lanchonete_design_patterns_api.model.Lanche;
import lanchonete_design_patterns_api.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class LancheFactory extends ProdutoFactory {

    @Override
    public Produto criarProduto(String nome, double preco) {
        return new Lanche(nome, preco);
    }

    @Override
    public TipoProduto getTipoSuportado() {
        return TipoProduto.LANCHE;
    }
}