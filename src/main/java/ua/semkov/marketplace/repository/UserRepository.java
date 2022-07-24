package ua.semkov.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.semkov.marketplace.model.Product;
import ua.semkov.marketplace.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByProduct(Product product);
}

