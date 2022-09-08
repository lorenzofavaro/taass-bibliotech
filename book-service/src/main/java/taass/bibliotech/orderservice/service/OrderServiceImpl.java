package taass.bibliotech.orderservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taass.bibliotech.dto.BookOrderDto;
import taass.bibliotech.events.inventory.InventoryEvent;
import taass.bibliotech.events.inventory.InventoryStatus;
import taass.bibliotech.events.order.OrderEvent;
import taass.bibliotech.events.order.OrderStatus;
import taass.bibliotech.orderservice.entity.Order;
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

        if (getCurrentOrder(accountId) != null) {
            return null;
        }
        orderRepository.save(order);
        Long productId = order.getProducts().iterator().next().getProductId();

        BookOrderDto bookOrderDto = BookOrderDto.of(
                order.getId(),
                productId,
                order.getUserId()
        );

        var orderEvent = new OrderEvent(bookOrderDto, OrderStatus.ORDER_CREATED);

        rabbitTemplate.convertAndSend(exchange, routingkey, orderEvent);
        return order;
    }

    @Override
    @Transactional
    public Boolean cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).get();
        Long productId = order.getProducts().iterator().next().getProductId();

        BookOrderDto bookOrderDto = BookOrderDto.of(
                order.getId(),
                productId,
                order.getUserId()
        );
        var orderEvent = new OrderEvent(bookOrderDto, OrderStatus.ORDER_CANCELLED);

        rabbitTemplate.convertAndSend(exchange, routingkey, orderEvent);
        return true;
    }

    @Override
    @Transactional
    public Boolean returnOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).get();
        Long productId = order.getProducts().iterator().next().getProductId();

        BookOrderDto bookOrderDto = BookOrderDto.of(
                order.getId(),
                productId,
                order.getUserId()
        );
        var orderEvent = new OrderEvent(bookOrderDto, OrderStatus.ORDER_RETURNED);

        rabbitTemplate.convertAndSend(exchange, routingkey, orderEvent);
        return true;
    }

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void receiveOrderMessage(InventoryEvent inventoryEvent) {
        System.out.println("RECEIVED INVENTORY EVENT: " + inventoryEvent.toString());

        Order order = orderRepository.findById(inventoryEvent.getOrderId()).get();
        switch (inventoryEvent.getStatus()) {
            case RESERVED:
                order.setOrderStatus(OrderStatus.ORDER_COMPLETED);
                break;

            case REJECTED:
                order.setOrderStatus(OrderStatus.ORDER_CANCELLED);
                break;

            case RETURNED:
                order.setOrderStatus(OrderStatus.ORDER_RETURNED);
                break;
        }
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

    @Override
    public Order getOrderFromProductId(Long userId, Long productId) {
        List<Order> dbOrders = orderRepository.findAllByUserId(userId);
        if (dbOrders != null && dbOrders.size() > 0) {
            for (Order dbOrder : dbOrders) {
                LocalDate currentDateMinus30Days = LocalDate.now().minusDays(30);
                boolean isOrderRecent = dbOrder.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(currentDateMinus30Days);
                boolean isOrderCompleted = dbOrder.getOrderStatus() == OrderStatus.ORDER_COMPLETED;
                boolean bookOrderExists = dbOrder.getProducts().stream().anyMatch(x -> Objects.equals(x.getProductId(), productId));
                if (isOrderRecent && bookOrderExists && isOrderCompleted) {
                    return dbOrder;
                }
            }
        }
        return null;
    }

    @Override
    public Order getCurrentOrder(Long userId) {
        List<Order> dbOrders = orderRepository.findAllByUserId(userId);
        if (dbOrders != null) {
            for (Order dbOrder : dbOrders) {
                LocalDate currentDateMinus30Days = LocalDate.now().minusDays(30);
                boolean isOrderRecent = dbOrder.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(currentDateMinus30Days);
                boolean isOrderCompleted = dbOrder.getOrderStatus() == OrderStatus.ORDER_COMPLETED;
                if (isOrderRecent && isOrderCompleted) {
                    return dbOrder;
                }
            }
        }
        return null;
    }
}
