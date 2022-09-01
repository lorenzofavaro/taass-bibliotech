package taass.bibliotech.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taass.bibliotech.catalogservice.entity.ProductConsumption;

import java.util.UUID;

public interface ProductConsumptionRepository extends JpaRepository<ProductConsumption, UUID> {
}
