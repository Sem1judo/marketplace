package ua.semkov.marketplace.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ua.semkov.marketplace.exceptions.*;
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


    public List<User> findAllByProduct(Product product) {
        return userRepository.findAllByProduct(product);
    }

    public List<Product> findAllByUser(User user) {
        return productRepository.findAllByUser(user);
    }


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

    public List<Product> getListProductsByUser(User user) {
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


        if (user.getAmountOfMoney().compareTo(product.getPrice()) >= 0) {
            try {
                user.getProducts().add(product);
                product.getUsers().add(user);
                userRepository.save(user);
                productRepository.save(product);
            } catch (DataAccessException e) {
                log.error("Failed to add product to user: {}", user, e);
                throw new ServiceException("Failed to add product to user", e);
            }
        } else throw new NotEnoughAmountOfMoney("Amount of money is lower than product");
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

    public void deleteUser(User user) {
        log.debug("Trying to delete user = {}", user);

        if (user == null) {
            throw new ServiceException("not found user");
        }
        try {
            userRepository.delete(user);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Not existing user = {}", user);
            throw new UserNotFoundException("not exist user");
        } catch (DataAccessException e) {
            log.error("failed to delete user = {}", user, e);
            throw new ServiceException("Failed to delete user", e);
        }
    }
    public void deleteProduct(Product product) {
        log.debug("Trying to delete product = {}", product);

        if (product == null) {
            throw new ServiceException("not found product");
        }
        try {
            productRepository.delete(product);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Not existing product = {}", product);
            throw new ProductNotFoundException("not exist product");
        } catch (DataAccessException e) {
            log.error("failed to delete product = {}", product, e);
            throw new ServiceException("Failed to delete product", e);
        }
    }
}
