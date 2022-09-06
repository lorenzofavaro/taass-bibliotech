package taass.bibliotech.catalogservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import taass.bibliotech.catalogservice.entity.Product;

import java.util.List;

public interface CatalogService {

    List<Product> getAllProducts();

    Page<Product> findArticlesByCriteria(PageRequest pageable, List<String> categories, String search);

    List<String> getAllCategories();

    List<Product> getProductsByCategory(String category);

    List<Product> findFeaturedProducts();
}
