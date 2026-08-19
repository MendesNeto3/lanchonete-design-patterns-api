package lanchonete_design_patterns_api.Factory;

import lanchonete_design_patterns_api.enums.TipoProduto;
import lanchonete_design_patterns_api.model.Produto;
import lanchonete_design_patterns_api.model.Sobremesa;
import org.springframework.stereotype.Component;

@Component
public class SobremesaFactory extends ProdutoFactory {
    @Override
    public Produto criarProduto(String nome, double preco) {
        return new Sobremesa(nome, preco);
    }

    @Override
    public TipoProduto getTipoSuportado() {
        return TipoProduto.SOBREMESA;
    }
}
