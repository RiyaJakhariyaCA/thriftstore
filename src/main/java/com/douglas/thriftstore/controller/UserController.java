package com.douglas.thriftstore.controller;

import com.douglas.thriftstore.model.User;
import com.douglas.thriftstore.service.UserService;
import com.douglas.thriftstore.utils.StringValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080/")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create or Update User
    @PostMapping("/save")
    public ResponseEntity<String> saveOrUpdateUser(@RequestBody User user) {
        try {
        	
        	 if (userService.emailExists(StringValidation.removeWhiteSpaces(user.getEmail()))) {
        		 return ResponseEntity.ok("Email already exists. ");
             }
        	 
        	 if (userService.useridExists(StringValidation.removeWhiteSpaces(user.getUserid()))) {
        		 return ResponseEntity.ok("Userid already exists. ");
             }
        
            return ResponseEntity.ok(userService.saveUser(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while saving/updating user: " + e.getMessage());
        }
    }

    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found with ID: " + id);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while fetching user: " + e.getMessage());
        }
    }

    // Get All Users
    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (users == null || users.isEmpty()) {
                return ResponseEntity.status(404).body("No users found.");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while fetching users: " + e.getMessage());
        }
    }

    // Delete User by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable long id) {
        try {
            String response = userService.deleteUserById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while deleting user: " + e.getMessage());
        }
    }

    // Get User by userid
    @GetMapping("/userid/{userid}")
    public ResponseEntity<Object> getUserByUserid(@PathVariable String userid) {
        try {
            User user = userService.getUserByUserid(userid);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found with userid: " + userid);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while fetching user by userid: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String userid, @RequestParam String password) {
        try {
            return userService.authenticateUser(userid, password);
        } catch (Exception e) {
            return "Error during authentication: " + e.getMessage();
        }
    }
    
    @PutMapping("/deactivate")
    public String deactivateUser(@RequestParam String userid) {
        try {
            return userService.deactivateUser(userid);
        } catch (Exception e) {
            return "Error deactivating user: " + e.getMessage();
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody User user) {
        try {
        
        	
            String response = userService.updateUser(id, user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while updating user: " + e.getMessage());
        }
    }
    
}
