package com.douglas.thriftstore.controller;

import com.douglas.thriftstore.model.User;
import com.douglas.thriftstore.service.UserService;
import com.douglas.thriftstore.utils.StringValidation;

import co.douglas.thriftstore.dto.NormalResponse;
import co.douglas.thriftstore.dto.UserLoginRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create or Update User
    @PostMapping("/save")
    public ResponseEntity<Object> saveOrUpdateUser(@RequestBody User user) {
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
    public ResponseEntity<Object> deleteUserById(@PathVariable long id) {
        try {
        	NormalResponse response = userService.deleteUserById(id);
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
    
    @CrossOrigin()
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestDTO user) {
        try {
            return ResponseEntity.ok(userService.authenticateUser(user.username, user.password));
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(500).body("Error during authentication: " + e.getMessage());
        }
    }
    
    @PutMapping("/deactivate")
    public ResponseEntity<Object> deactivateUser(@RequestParam String userid) {
        try {
            return ResponseEntity.ok(userService.deactivateUser(userid));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deactivating user: " + e.getMessage());
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id, @RequestBody User user) {
        try {
        
        	
            NormalResponse response = userService.updateUser(id, user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while updating user: " + e.getMessage());
        }
    }
    
}
