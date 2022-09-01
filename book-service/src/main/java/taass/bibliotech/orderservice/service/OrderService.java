package taass.bibliotech.orderservice.service;

import taass.bibliotech.orderservice.entity.Order;
import taass.bibliotech.orderservice.modal.OrderForm;

import java.util.List;
import java.util.UUID;


public interface OrderService {
    Order createOrder(OrderForm orderForm, Long accountId);

    List<Order> getAllOrders(Long userId);

    Order getOrder(Long userId, UUID orderId);
}
