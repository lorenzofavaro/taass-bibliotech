package taass.bibliotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderDto {
    private UUID orderId;
    private Set<Triple> products;
    private Double price;
    private Long userId;

    @Override
    public String toString() {
        return "PurchaseOrderDto{" +
                "orderId=" + orderId +
                ", products=" + products +
                ", price=" + price +
                ", userId=" + userId +
                '}';
    }
}
