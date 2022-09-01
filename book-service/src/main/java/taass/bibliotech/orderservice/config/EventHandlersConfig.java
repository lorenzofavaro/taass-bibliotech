//package taass.bibliotech.orderservice.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import taass.bibliotech.events.inventory.InventoryEvent;
//import taass.bibliotech.events.payment.PaymentEvent;
//
//import java.util.function.Consumer;
//
//@Configuration
//public class EventHandlersConfig {
//
//    @Autowired
//    private OrderStatusUpdateEventHandler orderEventHandler;
//
//    @Bean
//    public Consumer<InventoryEvent> inventoryEventConsumer(){
//        return invetoryEvent -> {
//            orderEventHandler.updateOrder(invetoryEvent.getInventory().getOrderId(), po -> {
//                po.setInventoryStatus(invetoryEvent.getStatus());
//            });
//        };
//    }
//
//}
