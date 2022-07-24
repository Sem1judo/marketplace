package ua.semkov.marketplace.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ua.semkov.marketplace.exceptions.ProductAlreadyExistException;
import ua.semkov.marketplace.exceptions.ProductNotFoundException;
import ua.semkov.marketplace.exceptions.UserAlreadyExistException;
import ua.semkov.marketplace.exceptions.UserNotFoundException;
import ua.semkov.marketplace.model.Product;
import ua.semkov.marketplace.model.User;
import ua.semkov.marketplace.repository.ProductRepository;
import ua.semkov.marketplace.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SystemService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public List<User> getListUsers() {
        log.debug("Trying to get list of users");
        try {
            return userRepository.findAll();
        } catch (EmptyResultDataAccessException e) {
            log.warn("Users is not exist");
            throw new UserNotFoundException("Doesn't exist such users");
        } catch (DataAccessException e) {
            log.error("Failed to get list of users", e);
            throw new ServiceException("Failed to get list of users", e);
        }
    }

    public List<Product> getListProducts() {
        log.debug("Trying to get list of products");
        try {
            return productRepository.findAll();
        } catch (EmptyResultDataAccessException e) {
            log.warn("Products is not exist");
            throw new ProductNotFoundException("Doesn't exist such products");
        } catch (DataAccessException e) {
            log.error("Failed to get list of products", e);
            throw new ServiceException("Failed to get list of products", e);
        }
    }


    public void addUserProduct(User user, Product product) {
        log.debug("Trying to add product:{} to user:{}", user, product);

        if (user.getAmountOfMoney().compareTo(product.getPrice()) > 0) {
            user.setProducts((List<Product>) product);
            try {
                return userRepository.save(product);
            } catch (DataAccessException e) {
                log.error("Failed to add product: {}", product, e);
                throw new ServiceException("Failed to add product", e);
            }
        }
    }


    public User addUser(User user) throws UserAlreadyExistException {
        log.debug("Trying to add user: {}", user);

        try {
            return userRepository.save(user);
        } catch (DataAccessException e) {
            log.error("Failed to add user: {}", user, e);
            throw new ServiceException("Failed to add user", e);
        }
    }

    public Product addProduct(Product product) throws ProductAlreadyExistException {
        log.debug("Trying to add product: {}", product);

        try {
            return productRepository.save(product);
        } catch (DataAccessException e) {
            log.error("Failed to add product: {}", product, e);
            throw new ServiceException("Failed to add product", e);
        }
    }

}
