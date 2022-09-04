package taass.bibliotech.catalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import taass.bibliotech.catalogservice.entity.Category;
import taass.bibliotech.catalogservice.entity.Product;
import taass.bibliotech.catalogservice.repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllEagerBy();
    }

    private final int featuredProductsNumber = 18;

    @Override
    public Page<Product> findArticlesByCriteria(PageRequest pageable, List<String> categories, String search) {
        return productRepository.findAll(ProductSpecification.filterBy(categories, search), pageable);
    }

    @Override
    public List<String> getAllCategories() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Category> categories = categoryRepository.findByName(category);
        List<Product> products = new ArrayList<>();
        for (Category c : categories) {
            products.add(c.getProduct());
        }

        return products;
    }

    @Override
    public List<Product> findFeaturedProducts() {
        return productRepository.findAll(PageRequest.of(0, featuredProductsNumber)).getContent();
    }
}
