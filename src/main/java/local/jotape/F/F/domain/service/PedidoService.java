package local.jotape.F.F.domain.service;

import java.time.LocalDateTime;
import local.jotape.F.F.domain.model.Pedido;
import local.jotape.F.F.domain.model.StatusPedido;
import local.jotape.F.F.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    public Pedido criar(Pedido pedido) {
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setDataAbertura(LocalDateTime.now());
        
        return pedidoRepository.save(pedido);
    }

}
