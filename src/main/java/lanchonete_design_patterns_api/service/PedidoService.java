package lanchonete_design_patterns_api.service;

import lanchonete_design_patterns_api.Factory.ProdutoFactoryProvider;
import lanchonete_design_patterns_api.adapter.ProcessadorPagamento;
import lanchonete_design_patterns_api.builder.PedidoBuilder;
import lanchonete_design_patterns_api.chain.ValidadorPedido;
import lanchonete_design_patterns_api.decorator.AdicionalDecoratorFactory;
import lanchonete_design_patterns_api.dto.CriarPedidoRequest;
import lanchonete_design_patterns_api.dto.FormaPagamentoRequest;
import lanchonete_design_patterns_api.dto.ItemRequest;
import lanchonete_design_patterns_api.enums.StatusPedido;
import lanchonete_design_patterns_api.exception.PedidoNaoEncontradoException;
import lanchonete_design_patterns_api.model.Pedido;
import lanchonete_design_patterns_api.model.Produto;
import lanchonete_design_patterns_api.observer.PedidoSubject;
import lanchonete_design_patterns_api.repository.PedidoRepository;
import lanchonete_design_patterns_api.singleton.Estoque;
import lanchonete_design_patterns_api.strategy.FormaPagamento;
import lanchonete_design_patterns_api.strategy.PagamentoCartao;
import lanchonete_design_patterns_api.strategy.PagamentoDinheiro;
import lanchonete_design_patterns_api.strategy.PagamentoPix;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final ProdutoFactoryProvider factoryProvider;
    private final ValidadorPedido cadeiaValidacao;
    private final Estoque estoque;
    private final PedidoSubject pedidoSubject;
    private final PedidoRepository pedidoRepository;
    private final ProcessadorPagamento processadorPagamento;

    public PedidoService(ProdutoFactoryProvider factoryProvider,
                         @Qualifier("cadeiaValidacao") ValidadorPedido cadeiaValidacao,
                         Estoque estoque,
                         PedidoSubject pedidoSubject,
                         PedidoRepository pedidoRepository,
                         ProcessadorPagamento processadorPagamento) {
        this.factoryProvider = factoryProvider;
        this.cadeiaValidacao = cadeiaValidacao;
        this.estoque = estoque;
        this.pedidoSubject = pedidoSubject;
        this.pedidoRepository = pedidoRepository;
        this.processadorPagamento = processadorPagamento;
    }

    public Pedido criarPedido(CriarPedidoRequest request) {
        List<Produto> itens = request.itens().stream()
                .map(this::criarItem)
                .toList();

        FormaPagamento formaPagamento = request.formaPagamento() != null
                ? construirFormaPagamento(request.formaPagamento())
                : null;

        PedidoBuilder builder = new PedidoBuilder().paraCliente(request.cliente());
        itens.forEach(builder::adicionarItem);
        if (formaPagamento != null) {
            builder.comFormaPagamento(formaPagamento);
        }
        Pedido pedido = builder.build();

        cadeiaValidacao.validar(pedido);

        for (Produto item : itens) {
            estoque.baixarEstoque(item.getNome());
        }

        return pedidoRepository.salvar(pedido);
    }

    private Produto criarItem(ItemRequest item) {
        Produto base = factoryProvider.criar(item.tipo(), item.nome(), item.preco());
        return AdicionalDecoratorFactory.aplicar(base, item.adicionais());
    }
    private FormaPagamento construirFormaPagamento(FormaPagamentoRequest dto) {
        return switch (dto.tipo()) {
            case PIX -> new PagamentoPix();
            case DINHEIRO -> new PagamentoDinheiro(dto.valorRecebido() != null ? dto.valorRecebido() : 0);
            case CARTAO -> new PagamentoCartao(
                    dto.numeroCartao(),
                    dto.parcelas() != null ? dto.parcelas() : 1,
                    processadorPagamento
            );
        };
    }

    public Pedido buscarPorId(String id) {
        return pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.listarTodos();
    }

    public Pedido atualizarStatus(String id, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(novoStatus);
        pedidoSubject.notificarTodos(pedido);
        return pedido;
    }

    public Pedido processarPagamento(String id) {
        Pedido pedido = buscarPorId(id);
        if (pedido.getFormaPagamento() == null) {
            throw new IllegalStateException("Pedido nao possui forma de pagamento definida.");
        }
        boolean aprovado = pedido.getFormaPagamento().pagar(pedido.getValorTotal());
        pedido.setPago(aprovado);
        if (!aprovado) {
            throw new IllegalStateException("Pagamento nao foi aprovado.");
        }
        return pedido;
    }
}
