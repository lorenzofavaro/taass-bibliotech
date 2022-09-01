//package taass.bibliotech.orderservice.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import taass.bibliotech.events.inventory.InventoryStatus;
//import taass.bibliotech.events.order.OrderStatus;
//import taass.bibliotech.orderservice.entity.Order;
//import taass.bibliotech.orderservice.repository.OrderRepository;
//import taass.bibliotech.orderservice.service.OrderStatusPublisher;
//
//import javax.transaction.Transactional;
//import java.util.Objects;
//import java.util.UUID;
//import java.util.function.Consumer;
//
//@Service
//public class OrderStatusUpdateEventHandler {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private OrderStatusPublisher publisher;
//
//    @Transactional
//    public void updateOrder(final UUID id, Consumer<Order> consumer) {
//        orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
//    }
//
//    private void updateOrder(Order purchaseOrder) {
//        if (Objects.isNull(purchaseOrder.getInventoryStatus())) {
//            return;
//        }
//        //se il libro richiesto sono stati riservati l'ordine è COMPLETED.
//        boolean isComplete = InventoryStatus.RESERVED.equals(purchaseOrder.getInventoryStatus());
//        var orderStatus = isComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
//        purchaseOrder.setOrderStatus(orderStatus);
//        if (!isComplete) {
//            this.publisher.raiseOrderEvent(purchaseOrder, orderStatus);
//        }
//        orderRepository.save(purchaseOrder);
//    }
//}
//
