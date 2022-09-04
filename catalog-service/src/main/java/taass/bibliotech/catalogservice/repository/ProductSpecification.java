package taass.bibliotech.catalogservice.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import taass.bibliotech.catalogservice.entity.Category;
import taass.bibliotech.catalogservice.entity.Product;


public class ProductSpecification {

    private ProductSpecification() {
    }

    @SuppressWarnings("serial")
    public static Specification<Product> filterBy(List<String> categories, String search) {
        return (Specification<Product>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            query.distinct(true);

            if (categories != null && !categories.isEmpty()) {
                Join<Product, Category> joinSize = root.join("categories");
                predicates.add(criteriaBuilder.and(joinSize.get("name").in(categories)));
            }


            if (search != null && !search.isEmpty()) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + search + "%")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}

