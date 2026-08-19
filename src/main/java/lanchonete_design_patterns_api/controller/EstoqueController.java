package lanchonete_design_patterns_api.controller;

import lanchonete_design_patterns_api.singleton.Estoque;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final Estoque estoque;

    public EstoqueController(Estoque estoque) {
        this.estoque = estoque;
    }

    @GetMapping
    public ResponseEntity<Map<String, Integer>> listar() {
        return ResponseEntity.ok(estoque.getTodos());
    }
}
