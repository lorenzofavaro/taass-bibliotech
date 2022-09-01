package taass.bibliotech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PaymentDto {

    private UUID orderId;
    private Long userId;
    private double amount;

    @Override
    public String toString() {
        return "PaymentDto{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
