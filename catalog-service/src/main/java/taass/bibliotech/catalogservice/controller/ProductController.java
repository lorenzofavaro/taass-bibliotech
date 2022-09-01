package taass.bibliotech.catalogservice.controller;


import taass.bibliotech.catalogservice.entity.Product;
import taass.bibliotech.catalogservice.models.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.bibliotech.catalogservice.service.ProductService;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/add")
    public HttpEntity<String> addProduct(@Valid @RequestBody ProductForm productForm) {
        productService.addProduct(productForm);
        return  ResponseEntity.ok("Product added");
    }

    @PostMapping("/product/edit")
    public HttpEntity<String> editProduct(@Valid @RequestBody ProductForm productForm) throws Exception {
        productService.editProduct(productForm);
        return  ResponseEntity.ok("Product edited");
    }

    @DeleteMapping("/product/delete/{idProduct}")
    public HttpEntity<String> deleteProduct(@PathVariable Long idProduct) throws Exception {
        productService.deleteProduct(idProduct);
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping("/product/{idProduct}")
    public ResponseEntity<Product> getProduct(@PathVariable Long idProduct) throws Exception {
        Product product = productService.getProduct(idProduct);
        return ResponseEntity.ok(product);
    }
}
