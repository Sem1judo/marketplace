package ua.semkov.marketplace.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.semkov.marketplace.repository.ProductRepository;


@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


}
