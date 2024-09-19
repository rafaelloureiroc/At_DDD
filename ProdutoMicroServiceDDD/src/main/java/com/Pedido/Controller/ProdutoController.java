package com.Pedido.Controller;

import com.Pedido.Model.Produto;
import com.Pedido.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/create")
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto createdProduto = produtoService.saveProduto(produto);
        return ResponseEntity.ok(createdProduto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.getProdutoById(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/atualizar")
    public ResponseEntity<Void> atualizarQuantidade(@PathVariable Long id, @RequestParam Integer quantidade) {
        produtoService.atualizarQuantidade(id, quantidade);
        return ResponseEntity.ok().build();
    }

}
