package edu.spring.cloud.catalogservice.dao;

import edu.spring.cloud.catalogservice.model.internal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
}
