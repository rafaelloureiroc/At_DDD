package com.AtDDD.HistoricoPedido.controller;

import com.AtDDD.HistoricoPedido.event.PedidoFeitoEvent;
import com.AtDDD.HistoricoPedido.service.HistoricoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoPedidoController {

    @Autowired
    private HistoricoPedidoService historicoPedidoService;

    @GetMapping("/pedidos")
    public List<PedidoFeitoEvent> getTodosPedidos() {
        return historicoPedidoService.getTodosPedidos();
    }
}
