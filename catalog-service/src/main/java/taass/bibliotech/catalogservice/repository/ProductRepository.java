package taass.bibliotech.catalogservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import taass.bibliotech.catalogservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @EntityGraph(attributePaths = {"categories"})
    List<Product> findAllEagerBy();

    @EntityGraph(attributePaths = {"categories"})
    Optional<Product> findById(Long id);
}
