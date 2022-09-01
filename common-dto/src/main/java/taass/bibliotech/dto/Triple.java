package taass.bibliotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Triple {
    private Long productId;
    private Integer quantity;
    private Integer size;

    @Override
    public String toString() {
        return "Triple{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", size=" + size +
                '}';
    }
}
