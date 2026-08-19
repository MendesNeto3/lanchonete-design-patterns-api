package lanchonete_design_patterns_api.model;

public class Bebida implements Produto {

    private final String nome;
    private final double preco;

    public Bebida (String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public String getNome() {
        return "";
    }

    @Override
    public double getPreco() {
        return 0;
    }

    @Override
    public String getDescricao() {
        return "";
    }
}
