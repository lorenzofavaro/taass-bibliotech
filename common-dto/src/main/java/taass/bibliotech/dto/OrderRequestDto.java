package taass.bibliotech.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

@Data
public class OrderRequestDto {

    private Long userId;
    private HashMap<Long, Integer> products;
    private UUID orderId;
    private double price;

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "userId=" + userId +
                ", products=" + products +
                ", orderId=" + orderId +
                ", price=" + price +
                '}';
    }
}
