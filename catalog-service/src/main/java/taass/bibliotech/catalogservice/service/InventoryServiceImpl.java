package taass.bibliotech.catalogservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import taass.bibliotech.catalogservice.entity.Product;
import taass.bibliotech.catalogservice.repository.ProductRepository;
import taass.bibliotech.dto.Triple;
import taass.bibliotech.events.inventory.InventoryEvent;
import taass.bibliotech.events.inventory.InventoryStatus;
import taass.bibliotech.events.order.OrderEvent;

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
        System.out.println("RECEIVED BOOKING BOOK: " + orderEvent.toString());
        InventoryEvent inventoryEvent = null;
        Long productId = 0L;
        Optional<Product> optProduct = null;
        for (Triple entry : orderEvent.getPurchaseOrder().getProducts()) {
            productId = entry.getProductId();
            optProduct = productRepository.findById(productId);
            int quantity = entry.getQuantity();
            if (optProduct == null || optProduct.isEmpty() || optProduct.get().getStock() < quantity)
                inventoryEvent = new InventoryEvent(orderEvent.getPurchaseOrder().getOrderId(), InventoryStatus.REJECTED); // Il libro che si voleva prenotare non è più disponibile.
        }

        if (inventoryEvent == null) {
            Product product = optProduct.get();
            product.setStock(product.getStock()-1); //Aggiorno la disponibilità del libro
            productRepository.save(product);
            inventoryEvent = new InventoryEvent(orderEvent.getPurchaseOrder().getOrderId(), InventoryStatus.RESERVED);
        }

        rabbitTemplate.convertAndSend(exchange, routingkey, inventoryEvent);
    }
}
