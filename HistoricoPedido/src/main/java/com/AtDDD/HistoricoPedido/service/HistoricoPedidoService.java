package com.AtDDD.HistoricoPedido.service;

import com.AtDDD.HistoricoPedido.event.PedidoFeitoEvent;
import com.AtDDD.HistoricoPedido.repository.HistoricoPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoPedidoService {

    @Autowired
    private HistoricoPedidoRepository historicoPedidoRepository;

    public List<PedidoFeitoEvent> getTodosPedidos() {
        return historicoPedidoRepository.findAll();
    }

    public PedidoFeitoEvent salvarPedidoFeito(PedidoFeitoEvent pedidoFeitoEvent) {
        return historicoPedidoRepository.save(pedidoFeitoEvent);
    }
}
