package local.jotape.F.F.domain.repository;

import java.util.List;
import local.jotape.F.F.domain.model.Pedido;
import local.jotape.F.F.domain.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(StatusPedido status);
}
