package ua.semkov.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.semkov.marketplace.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

