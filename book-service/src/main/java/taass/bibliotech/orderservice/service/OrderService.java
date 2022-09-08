package taass.bibliotech.orderservice.service;

import taass.bibliotech.orderservice.entity.Order;
import taass.bibliotech.orderservice.modal.OrderForm;

import java.util.List;
import java.util.UUID;


public interface OrderService {
    Order createOrder(OrderForm orderForm, Long accountId);

    Boolean cancelOrder(UUID orderId);

    List<Order> getAllOrders(Long userId);

    Order getOrder(Long userId, UUID orderId);

    Order getCurrentOrder(Long userId);

    Order getOrderFromProductId(Long userId, Long productId);
}
