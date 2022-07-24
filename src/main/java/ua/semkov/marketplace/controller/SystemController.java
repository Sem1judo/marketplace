package ua.semkov.marketplace.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.semkov.marketplace.model.Product;
import ua.semkov.marketplace.model.User;
import ua.semkov.marketplace.service.SystemService;

import java.awt.print.Book;
import java.util.List;

@RestController
public class SystemController {

    @Autowired
    SystemService systemService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        systemService.addUser(user);
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        systemService.addProduct(product);
    }

    @GetMapping("/getListUsers")
    public List<User> getAllUsers() {
        return systemService.getListUsers();
    }

    @GetMapping("/getListProducts")
    public List<Product> getAllProducts() {
        return systemService.getListProducts();
    }

    @GetMapping("/findAllUsersByProduct/{id}")
    public List<User> getUsersByProduct(@PathVariable("id") Long id) {
        return systemService.findAllByProduct(id);
    }

    @GetMapping("/findAllProductByUser/{id}")
    public List<Product> getProductsByUser(@PathVariable("id") Long id) {
        return systemService.findAllByUser(id);
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
