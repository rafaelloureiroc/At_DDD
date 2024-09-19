package com.AtDDD.HistoricoPedido.repository;


import com.AtDDD.HistoricoPedido.event.PedidoFeitoEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoPedidoRepository extends JpaRepository<PedidoFeitoEvent, String> {
}