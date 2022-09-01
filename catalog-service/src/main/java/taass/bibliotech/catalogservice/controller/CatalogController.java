package taass.bibliotech.catalogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taass.bibliotech.catalogservice.entity.Product;
import taass.bibliotech.catalogservice.models.FilterCatalogForm;
import taass.bibliotech.catalogservice.models.SortFilter;
import taass.bibliotech.catalogservice.service.CatalogService;
import taass.bibliotech.catalogservice.service.ProductService;

import java.util.List;

@CrossOrigin
@RestController
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ProductService productService;

    @GetMapping("/catalog/allCatalog")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> catalog = catalogService.getAllProducts();
        return ResponseEntity.ok(catalog);
    }

    @GetMapping("/catalog/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = catalogService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/catalog/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = catalogService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/catalog/catalog")
    public ResponseEntity<List<Product>> filterCatalog(@RequestBody FilterCatalogForm filters) {
        Integer page = filters.getPage();
        int pagenumber = (page == null || page <= 0) ? 0 : page-1;
        SortFilter sortFilter = new SortFilter((filters.getSort()));
        Page<Product> pageResult = catalogService.findArticlesByCriteria(
                PageRequest.of(pagenumber, 9, sortFilter.getSortType()),
                filters.getCategory(), filters.getSearch());

        return ResponseEntity.ok(pageResult.getContent());
    }

    @GetMapping("/catalog/featured")
    public ResponseEntity<List<Product>> featuredProducts() {
        List<Product> products = catalogService.findFeaturedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/catalog/{idProduct}")
    public ResponseEntity<Product> getProduct(@PathVariable Long idProduct) throws Exception {
        Product product = productService.getProduct(idProduct);
        return ResponseEntity.ok(product);
    }
}

