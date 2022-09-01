package taass.bibliotech.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import taass.bibliotech.events.order.OrderStatus;
import taass.bibliotech.orderservice.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository  extends JpaRepository<Order, UUID> {

    List<Order> findAllByUserId(Long userId);

    Optional<Order> findByIdAndUserId(UUID orderId, Long userId);

//    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
