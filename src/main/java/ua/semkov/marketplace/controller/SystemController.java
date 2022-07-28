package ua.semkov.marketplace.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ua.semkov.marketplace.model.Product;
import ua.semkov.marketplace.model.User;
import ua.semkov.marketplace.service.SystemService;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class SystemController {

    SystemService systemService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        systemService.addUser(user);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        systemService.addProduct(product);
    }

    @PostMapping("/buyProduct/{id}")
    public void buyProduct(@RequestBody User user,@PathVariable long id) {
        Product product = systemService.findByIdProduct(id);
        systemService.addUserProduct(user, product);
    }

    @GetMapping("/findByIdUser/{id}")
    public User getUserById(@PathVariable long id) {
        return systemService.findByIdUser(id);
    }

    @GetMapping("/findByIdProduct/{id}")
    public Product getProductById(@PathVariable long id) {
        return systemService.findByIdProduct(id);
    }

    @GetMapping("/getListUsers")
    public List<User> getAllUsers() {
        return systemService.getListUsers();
    }

    @GetMapping("/getListProducts")
    public List<Product> getAllProducts() {
        return systemService.getListProducts();
    }

    @GetMapping("/findAllByProductId/{product_id}")
    public List<User> findAllByProductId(@PathVariable("product_id") Long product_id) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < systemService.findAllByProductId(product_id).size(); i++) {
            users.add(systemService.findByIdUser(systemService.getListUsers().get(i).getId()));
        }
        return users;
    }

    @GetMapping("/findAllByUserId/{user_id}")
    public List<Product> findAllByUserId(@PathVariable("user_id") Long user_id) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < systemService.findAllByUserId(user_id).size(); i++) {
            products.add(systemService.findByIdProduct(systemService.getListProducts().get(i).getId()));
        }
        return products;
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        systemService.deleteUserById(id);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        systemService.deleteProductById(id);
    }
}
