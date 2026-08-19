package lanchonete_design_patterns_api.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(String id) {
        super("Pedido nao encontrado: " + id);
    }
}
