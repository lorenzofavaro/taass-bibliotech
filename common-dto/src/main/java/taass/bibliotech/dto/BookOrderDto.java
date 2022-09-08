package taass.bibliotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BookOrderDto {
    private UUID orderId;
    private Long productId;
    private Long userId;

    @Override
    public String toString() {
        return "BookOrderDto{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", userId=" + userId +
                '}';
    }
}
