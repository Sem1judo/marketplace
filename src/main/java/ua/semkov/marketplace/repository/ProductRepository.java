package ua.semkov.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.semkov.marketplace.model.Product;
import ua.semkov.marketplace.model.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

   List<Product> findAllByUser(User user);}
