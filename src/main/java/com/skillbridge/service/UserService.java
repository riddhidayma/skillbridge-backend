package com.skillbridge.service;

import com.skillbridge.entity.User;
import com.skillbridge.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    // Constructor Injection (Best Practice)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // 1. Get the plain text from the Angular request
        String plainPassword = user.getPassword();

        // 2. Transform it using the BCrypt bean we created earlier
        String hashedPassword = encoder.encode(plainPassword);

        // 3. Put the SECURE version back into the user object
        user.setPassword(hashedPassword);

        // 4. Save to the database
        return userRepository.save(user);
    }



    public User getUserById(Long id) {
        // Find user by ID, throw an error if they don't exist
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User verifyLogin(String email, String rawPassword) {
        User user = userRepository.findByEmail(email).orElse(null);

        // We use encoder.matches() because we can't use .equals() on hashes!
        if (user != null && encoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }
}