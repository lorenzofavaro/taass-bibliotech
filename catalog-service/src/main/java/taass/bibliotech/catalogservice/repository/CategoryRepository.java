package taass.bibliotech.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import taass.bibliotech.catalogservice.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String category);

    @Query("SELECT DISTINCT c.name FROM Category c")
    List<String> findAllCategories();
}


/*
	@EntityGraph(attributePaths = { "sizes", "categories", "brands" })
	List<Article> findAllEagerBy();

	@EntityGraph(attributePaths = { "sizes", "categories", "brands" })
	Optional<Article> findById(Long id);

	*/
