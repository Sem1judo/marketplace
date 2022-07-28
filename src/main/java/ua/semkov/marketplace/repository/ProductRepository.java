package ua.semkov.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.semkov.marketplace.model.Product;
import ua.semkov.marketplace.model.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from user_products WHERE user_id = :id", nativeQuery = true)
    List<Product> findAllByUserId(@Param("id") long id);
}
