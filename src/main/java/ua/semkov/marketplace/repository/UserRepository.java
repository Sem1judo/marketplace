package ua.semkov.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.semkov.marketplace.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user_products WHERE product_id = ?1", nativeQuery = true)
    List<Long> findAllByProductId(Long product_id);
}

