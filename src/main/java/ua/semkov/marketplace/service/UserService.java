package ua.semkov.marketplace.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.semkov.marketplace.repository.UserRepository;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository productRepository;

}
