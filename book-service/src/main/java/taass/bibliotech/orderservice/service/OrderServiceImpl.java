package taass.bibliotech.orderservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taass.bibliotech.dto.PurchaseOrderDto;
import taass.bibliotech.dto.Triple;
import taass.bibliotech.events.inventory.InventoryEvent;
import taass.bibliotech.events.inventory.InventoryStatus;
import taass.bibliotech.events.order.OrderEvent;
import taass.bibliotech.events.order.OrderStatus;
import taass.bibliotech.orderservice.entity.Order;
import taass.bibliotech.orderservice.entity.OrderItem;
import taass.bibliotech.orderservice.modal.OrderForm;
import taass.bibliotech.orderservice.repository.OrderRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingkey;


    @Override
    @Transactional
    public Order createOrder(OrderForm orderForm, Long accountId) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(accountId);
        order.setProducts(orderForm.getProducts());
        order.setDate(new Date());
        order.setOrderStatus(OrderStatus.ORDER_CREATED);
        orderRepository.save(order);

        List<Order> dbOrders = orderRepository.findAllByUserId(accountId);
        if (dbOrders != null && dbOrders.size() > 0) {
            for (Order dbOrder : dbOrders) {
                Long productId = order.getProducts().iterator().next().getProductId();
                LocalDate currentDateMinus30Days = LocalDate.now().minusDays(30);
                boolean isOrderRecent = dbOrder.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(currentDateMinus30Days);
                boolean bookOrderExists = dbOrder.getProducts().stream().anyMatch(x -> Objects.equals(x.getProductId(), productId));
                if (isOrderRecent && bookOrderExists) {
                    InventoryEvent inventoryEvent = new InventoryEvent(order.getId(), InventoryStatus.REJECTED);
                    rabbitTemplate.convertAndSend(exchange, routingkey, inventoryEvent);
                    return null;
                }
            }
        }

        Set<Triple> products = new HashSet<>();
        for (OrderItem orderItem : order.getProducts()) {
            products.add(Triple.of(orderItem.getProductId(), 1, null));
        }

        PurchaseOrderDto purchaseOrderDto = PurchaseOrderDto.of(
                order.getId(),
                products,
                order.getTotal(),
                order.getUserId()
        );

        var orderEvent = new OrderEvent(purchaseOrderDto, OrderStatus.ORDER_CREATED);

        rabbitTemplate.convertAndSend(exchange, routingkey, orderEvent);
        return order;
    }

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void receiveOrderMessage(InventoryEvent inventoryEvent) {
        System.out.println("RECEIVED INVENTORY EVENT: " + inventoryEvent.toString());

        Order order = orderRepository.findById(inventoryEvent.getOrderId()).get();

        if (inventoryEvent.getStatus() == InventoryStatus.RESERVED)
            order.setOrderStatus(OrderStatus.ORDER_COMPLETED);
        else if (inventoryEvent.getStatus() == InventoryStatus.REJECTED)
            order.setOrderStatus(OrderStatus.ORDER_CANCELLED);

        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order getOrder(Long userId, UUID orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesn't exist"));
    }

}
