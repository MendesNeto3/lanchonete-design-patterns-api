package lanchonete_design_patterns_api.controller;

import jakarta.validation.Valid;
import lanchonete_design_patterns_api.dto.AtualizarStatusRequest;
import lanchonete_design_patterns_api.dto.CriarPedidoRequest;
import lanchonete_design_patterns_api.dto.PedidoResponse;
import lanchonete_design_patterns_api.model.Pedido;
import lanchonete_design_patterns_api.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody CriarPedidoRequest request) {
        Pedido pedido = pedidoService.criarPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscar(@PathVariable String id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(toResponse(pedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        List<PedidoResponse> resposta = pedidoService.listarTodos().stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizarStatus(@PathVariable String id,
                                                          @Valid @RequestBody AtualizarStatusRequest request) {
        Pedido pedido = pedidoService.atualizarStatus(id, request.status());
        return ResponseEntity.ok(toResponse(pedido));
    }

    @PostMapping("/{id}/pagamento")
    public ResponseEntity<PedidoResponse> processarPagamento(@PathVariable String id) {
        Pedido pedido = pedidoService.processarPagamento(id);
        return ResponseEntity.ok(toResponse(pedido));
    }

    private PedidoResponse toResponse(Pedido pedido) {
        List<String> itens = pedido.getItens().stream()
                .map(p -> p.getDescricao() + String.format(" (R$ %.2f)", p.getPreco()))
                .toList();
        String formaPagamento = pedido.getFormaPagamento() != null
                ? pedido.getFormaPagamento().getDescricao()
                : null;
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente(),
                itens,
                pedido.getValorTotal(),
                pedido.getStatus(),
                formaPagamento,
                pedido.isPago()
        );
    }
}
