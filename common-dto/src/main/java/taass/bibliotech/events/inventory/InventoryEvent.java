package taass.bibliotech.events.inventory;

import lombok.Data;
import taass.bibliotech.events.Event;

import java.util.Date;
import java.util.UUID;

@Data
public class InventoryEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();

    private UUID orderId;
    private InventoryStatus status;

    public InventoryEvent() {
    }

    public InventoryEvent(UUID orderId, InventoryStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    public UUID getOrderId() { return orderId; }

    public InventoryStatus getStatus() {
        return status;
    }

}
