package taass.bibliotech.catalogservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import taass.bibliotech.catalogservice.entity.Product;
import taass.bibliotech.catalogservice.models.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taass.bibliotech.catalogservice.repository.ProductRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(ProductForm productForm) {
        Product product = new Product();
        product.setTitle(productForm.getTitle());
        product.setAuthor(productForm.getAuthor());
        product.setPicture(productForm.getPicture());
        product.setDescription(productForm.getDescription());
        product.setStock(productForm.getStock());
        if (productForm.getCategories() != null)
            product.setCategories(productForm.getCategories());

        productRepository.save(product);
    }

    public void editProduct(ProductForm productForm) throws Exception {

        Product product = productRepository.findById(productForm.getId()).orElseThrow(() -> new Exception("Product doesnt exist"));

        product.setTitle(productForm.getTitle());
        product.setAuthor(productForm.getAuthor());
        product.setPicture(productForm.getPicture());
        product.setDescription(productForm.getDescription());
        product.setStock(productForm.getStock());
        product.setCategories(productForm.getCategories());

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) throws ResponseStatusException {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesnt exist"));

        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(Long idProduct) throws ResponseStatusException {
        Product product = productRepository.findById(idProduct).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesnt exist"));

        return product;
    }
}
