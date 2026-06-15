package com.skillbridge.controller;

import com.skillbridge.entity.User;
import com.skillbridge.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // API: POST http://localhost:8080/api/users/register
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // @RequestBody converts the incoming JSON into a Java 'User' object
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser); // Returns HTTP 200 OK with the saved data
    }

    // API: GET http://localhost:8080/api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // @PathVariable extracts the {id} from the URL
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Inside UserController.java

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        // Call the secure verification logic
        User user = userService.verifyLogin(loginData.getEmail(), loginData.getPassword());

        if (user != null) {
            // Jackson will automatically hide the password in the response
            // because of the @JsonProperty annotation you added earlier!
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Invalid email or password\"}");
        }
    }
}