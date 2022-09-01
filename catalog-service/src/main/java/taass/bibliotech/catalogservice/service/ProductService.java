package taass.bibliotech.catalogservice.service;

import taass.bibliotech.catalogservice.entity.Product;
import taass.bibliotech.catalogservice.models.ProductForm;

public interface ProductService {

    void addProduct(ProductForm productForm);

    void editProduct(ProductForm productForm) throws Exception;

    void deleteProduct(Long id) throws Exception;

    Product getProduct(Long idProduct) throws Exception;
}
