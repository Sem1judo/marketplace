package ua.semkov.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.semkov.marketplace.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
