package com.example.gestionhoteles.service;
import com.example.gestionhoteles.model.User;
import com.example.gestionhoteles.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findUser(username, password);
    }
    public void save(User user) {
        userRepository.save(user);
    }
}