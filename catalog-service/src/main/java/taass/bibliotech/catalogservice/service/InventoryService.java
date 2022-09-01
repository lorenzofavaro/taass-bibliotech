package taass.bibliotech.catalogservice.service;

import taass.bibliotech.events.order.OrderEvent;

public interface InventoryService {

    void receiveOrderMessage(OrderEvent orderEvent);
}
