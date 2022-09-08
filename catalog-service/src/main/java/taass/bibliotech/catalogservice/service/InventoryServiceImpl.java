package taass.bibliotech.catalogservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import taass.bibliotech.catalogservice.entity.Product;
import taass.bibliotech.catalogservice.repository.ProductRepository;
import taass.bibliotech.events.inventory.InventoryEvent;
import taass.bibliotech.events.inventory.InventoryStatus;
import taass.bibliotech.events.order.OrderEvent;
import taass.bibliotech.events.order.OrderStatus;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingkey;

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void receiveOrderMessage(OrderEvent orderEvent) {
        InventoryEvent inventoryEvent = new InventoryEvent(orderEvent.getBookOrder().getOrderId(), InventoryStatus.REJECTED);
        Long productId = orderEvent.getBookOrder().getProductId();
        Product product = productRepository.findById(productId).get();

        switch (orderEvent.getOrderStatus()) {
            case ORDER_CREATED:
                if (product.getStock() < 1) {
                    inventoryEvent = new InventoryEvent(orderEvent.getBookOrder().getOrderId(), InventoryStatus.REJECTED); // Il libro che si voleva prenotare non è più disponibile.
                } else {
                    product.setStock(product.getStock() - 1); // Aggiorno la disponibilità del libro
                    productRepository.save(product);
                    inventoryEvent = new InventoryEvent(orderEvent.getBookOrder().getOrderId(), InventoryStatus.RESERVED);
                }
                break;

            case ORDER_CANCELLED:
                product.setStock(product.getStock() + 1); // Aggiorno la disponibilità del libro
                productRepository.save(product);
                inventoryEvent = new InventoryEvent(orderEvent.getBookOrder().getOrderId(), InventoryStatus.REJECTED);
                break;

            case ORDER_RETURNED:
                product.setStock(product.getStock() + 1); // Aggiorno la disponibilità del libro
                productRepository.save(product);
                inventoryEvent = new InventoryEvent(orderEvent.getBookOrder().getOrderId(), InventoryStatus.RETURNED);
                break;
        }
        rabbitTemplate.convertAndSend(exchange, routingkey, inventoryEvent);
    }
}
