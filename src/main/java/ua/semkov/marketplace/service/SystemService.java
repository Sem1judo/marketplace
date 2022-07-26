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


    public List<Long> findAllByProductId(Long product_id) {
        log.debug("Trying to get list of users by product id{}", product_id);
        return userRepository.findAllByProductId(product_id);
    }

    public List<Long> findAllByUserId(Long user_id) {
        log.debug("Trying to get list of product by user id{}", user_id);
        return productRepository.findAllByUserId(user_id);
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

    public User findByIdUser(long id) {
        log.debug("Trying to get user by id={}", id);

        if (id == 0) {
            log.warn("Missing id user");
            throw new ServiceException("Missing id user");
        }
        User user;
        try {
            user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Invalid user ID"));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Not existing user by id={}", id);
            throw new UserNotFoundException("Not existing user by id =" + id);
        } catch (DataAccessException e) {
            log.error("Failed to retrieve user by id={}", id, e);
            throw new ServiceException("Failed to retrieve user by id", e);
        }
        return user;
    }

    public Product findByIdProduct(long id) {
        log.debug("Trying to get product by id={}", id);

        if (id == 0) {
            log.warn("Missing id product");
            throw new ServiceException("Missing id product");
        }
        Product product;
        try {
            product = productRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Invalid product ID"));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Not existing product by id={}", id);
            throw new UserNotFoundException("Not existing product by id =" + id);
        } catch (DataAccessException e) {
            log.error("Failed to retrieve product by id={}", id, e);
            throw new ServiceException("Failed to retrieve product by id", e);
        }
        return product;
    }

    public void addUserProduct(User user, Product product) {
        log.debug("Trying to add product:{} to user:{}", user, product);

        user = findByIdUser(user.getId());
        if (user.getAmountOfMoney().compareTo(product.getPrice()) >= 0) {
            try {
                user.getProducts().add(product);
                product.getUsers().add(user);
                user.setAmountOfMoney(user.getAmountOfMoney().subtract(product.getPrice()));

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

    public void deleteUserById(Long id) {
        log.debug("Trying to delete user = {}", id);

        if (id == null) {
            throw new ServiceException("not found user");
        }
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Not existing user = {}", id);
            throw new UserNotFoundException("not exist user");
        } catch (DataAccessException e) {
            log.error("failed to delete user = {}", id, e);
            throw new ServiceException("Failed to delete user", e);
        }
    }

    public void deleteProductById(Long id) {
        log.debug("Trying to delete product = {}", id);

        if (id == null) {
            throw new ServiceException("not found product");
        }
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Not existing product = {}", id);
            throw new ProductNotFoundException("not exist product");
        } catch (DataAccessException e) {
            log.error("failed to delete product = {}", id, e);
            throw new ServiceException("Failed to delete product", e);
        }
    }
}

