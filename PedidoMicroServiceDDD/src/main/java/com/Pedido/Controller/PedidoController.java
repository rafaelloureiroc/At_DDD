package com.Pedido.Controller;

import com.Pedido.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestParam Long produtoId, @RequestParam Integer quantidade, @RequestParam Double dinheiro) {
        try {
            pedidoService.criarPedido(produtoId, quantidade, dinheiro);
            return ResponseEntity.ok("Pedido criado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}