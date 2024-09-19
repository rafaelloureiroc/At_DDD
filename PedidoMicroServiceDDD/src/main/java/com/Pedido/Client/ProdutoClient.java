package com.Pedido.Client;

import com.Pedido.DTO.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ProdutoClient", url = "http://produto-service:8080")
public interface ProdutoClient {
    @GetMapping("/produtos/{id}")
    ProdutoDTO getProduto(@PathVariable("id") Long id);

    @PostMapping("/produtos/{id}/atualizar")
    void atualizarQuantidade(@PathVariable("id") Long id, @RequestParam("quantidade") Integer quantidade);
}