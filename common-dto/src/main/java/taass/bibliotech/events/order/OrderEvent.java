package taass.bibliotech.events.order;

import lombok.Data;
import taass.bibliotech.dto.BookOrderDto;
import taass.bibliotech.events.Event;

import java.util.Date;
import java.util.UUID;

@Data
public class OrderEvent implements Event {

    private final UUID eventId = UUID.randomUUID();
    private final Date date = new Date();
    private BookOrderDto bookOrder;
    private OrderStatus orderStatus;

    public OrderEvent() {
    }

    public OrderEvent(BookOrderDto bookOrder, OrderStatus orderStatus) {
        this.bookOrder = bookOrder;
        this.orderStatus = orderStatus;
    }

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    public BookOrderDto getBookOrder() {
        return this.bookOrder;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "eventId=" + eventId +
                ", date=" + date +
                ", bookOrder=" + bookOrder +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
