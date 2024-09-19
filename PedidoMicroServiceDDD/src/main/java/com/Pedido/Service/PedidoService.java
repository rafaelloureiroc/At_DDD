package com.Pedido.Service;

import com.Pedido.Client.ProdutoClient;
import com.Pedido.Model.Pedido;
import com.Pedido.DTO.ProdutoDTO;
import com.Pedido.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoClient estoqueClient;

    public void criarPedido(Long produtoId, Integer quantidade, Double dinheiro) {
        ProdutoDTO produto = estoqueClient.getProduto(produtoId);

        if (produto != null && produto.getQuantidade() >= quantidade) {
            Double precoTotal = produto.getPreco() * quantidade;

            if (dinheiro >= precoTotal) {
                Pedido pedido = new Pedido();
                pedido.setProdutoId(produtoId);
                pedido.setQuantidade(quantidade);
                pedidoRepository.save(pedido);

                estoqueClient.atualizarQuantidade(produtoId, produto.getQuantidade() - quantidade);
                System.out.println("Pedido criado com sucesso!");
            } else {
                throw new RuntimeException("Dinheiro insuficiente para realizar a compra");
            }
        } else {
            throw new RuntimeException("Estoque insuficiente");
        }
    }
}