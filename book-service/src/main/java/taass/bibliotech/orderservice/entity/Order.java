package taass.bibliotech.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import taass.bibliotech.events.inventory.InventoryStatus;
import taass.bibliotech.events.order.OrderStatus;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private UUID id;
    private Long userId;
    private Double total;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderItem> products = new HashSet<>();

    private OrderStatus orderStatus;
    private InventoryStatus inventoryStatus;

    private Date date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Long, Integer> products) {
        for (Long key : products.keySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(key);
            orderItem.setOrder(this);
            this.products.add(orderItem);
        }
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(double price) {
        this.total = price;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", total=" + total +
                ", products=" + products +
//                ", orderStatus=" + orderStatus +
//                ", paymentStatus=" + paymentStatus +
//                ", inventoryStatus=" + inventoryStatus +
                ", date=" + date +
                '}';
    }
}
