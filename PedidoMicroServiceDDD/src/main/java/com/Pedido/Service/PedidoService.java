package com.Pedido.Service;

import com.Pedido.Client.ProdutoClient;
import com.Pedido.Model.Pedido;
import com.Pedido.DTO.ProdutoDTO;
import com.Pedido.Repository.PedidoRepository;
import com.Pedido.event.PedidoFeitoEvent;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoClient estoqueClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private final static Logger logger = Logger.getLogger(PedidoService.class);
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 2000;

    public void criarPedido(Long produtoId, Integer quantidade, Double dinheiro) {
        ProdutoDTO produto = estoqueClient.getProduto(produtoId);

        if (produto != null && produto.getQuantidade() >= quantidade) {
            Double precoTotal = produto.getPreco() * quantidade;

            if (dinheiro >= precoTotal) {
                Pedido pedido = new Pedido();
                pedido.setProdutoId(produtoId);
                pedido.setQuantidade(quantidade);
                pedido.setDinheiro(dinheiro);
                pedidoRepository.save(pedido);

                estoqueClient.atualizarQuantidade(produtoId, produto.getQuantidade() - quantidade);
                System.out.println("Pedido criado com sucesso!");

                PedidoFeitoEvent event = new PedidoFeitoEvent(
                        pedido.getId(),
                        pedido.getProdutoId(),
                        pedido.getQuantidade(),
                        pedido.getDinheiro(),
                        "CREATED"
                );

                System.out.println("Tentando enviar evento PedidoFeito: ");

                CompletableFuture.runAsync(() -> {
                    boolean success = sendEventWithRetry(event, "pedidoDDDExchange", "pedidoFeito");
                    if (success) {
                        System.out.println("Evento pedidoFeito enviado com sucesso.");
                    } else {
                        System.out.println("Falha ao enviar evento pedidoFeito após 3 tentativas.");
                    }
                });
            } else {
                throw new RuntimeException("Dinheiro insuficiente para realizar a compra");
            }
        } else {
            throw new RuntimeException("Estoque insuficiente");
        }
    }

    private boolean sendEventWithRetry(Object event, String exchange, String routingKey) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                rabbitTemplate.convertAndSend(exchange, routingKey, event);
                return true;
            } catch (Exception e) {
                logger.error("Erro ao enviar evento (tentativa " + attempt + "): " + e.getMessage());
                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        return false;
    }
}