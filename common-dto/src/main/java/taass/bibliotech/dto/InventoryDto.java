package taass.bibliotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class InventoryDto {

    private UUID orderId;
    private Set<Triple> products;

    @Override
    public String toString() {
        return "InventoryDto{" +
                "orderId=" + orderId +
                ", products=" + products +
                '}';
    }
}
