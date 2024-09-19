package com.AtDDD.HistoricoPedido.listener;

import com.AtDDD.HistoricoPedido.event.PedidoFeitoEvent;
import com.AtDDD.HistoricoPedido.service.HistoricoPedidoService;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoFeitoListener {

    @Autowired
    private HistoricoPedidoService historicoPedidoService;

    private final static Logger logger = Logger.getLogger(PedidoFeitoListener.class);
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 2000;

    @RabbitListener(queues = "pedidoFeitoDDDQueue")
    public void handlePedidoFeitoEvent(PedidoFeitoEvent event) {

        System.out.println("Recebido evento pedidoFeito:" + event);


        try {
            historicoPedidoService.salvarPedidoFeito(event);
            System.out.println("Salvo com sucesso:" + event);
        } catch (Exception e) {
            System.out.println("Erro ao processar evento PedidoFeito"+ e);
        }
    }



}
