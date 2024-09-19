package event;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class PedidoFeitoEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private double dinheiro;
    private String status;


    public PedidoFeitoEvent(Long id, Long produtoId, Integer quantidade, double dinheiro, String status) {
        this.id = id;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.dinheiro = dinheiro;
        this.status = status;
    }
}