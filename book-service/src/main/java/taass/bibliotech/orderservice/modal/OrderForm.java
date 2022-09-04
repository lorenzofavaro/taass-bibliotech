package taass.bibliotech.orderservice.modal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.UUID;

public class OrderForm {

    @NotNull
    private HashMap<Long, Integer> products;

    public HashMap<Long, Integer> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "products=" + products +
                '}';
    }
}
