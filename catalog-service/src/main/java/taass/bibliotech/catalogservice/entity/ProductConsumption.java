package taass.bibliotech.catalogservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ProductConsumption {
    @Id
    private UUID orderId;
    private Long productId;
    private int quantityConsumed;

    public ProductConsumption(UUID orderId, Long productId, int quantityConsumed) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantityConsumed = quantityConsumed;
    }

    public ProductConsumption() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantityConsumed() {
        return quantityConsumed;
    }

    public void setQuantityConsumed(int quantityConsumed) {
        this.quantityConsumed = quantityConsumed;
    }
}
