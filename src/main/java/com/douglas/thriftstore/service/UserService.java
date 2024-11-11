package com.douglas.thriftstore.service;

import com.douglas.thriftstore.model.User;
import com.douglas.thriftstore.repository.UserRepository;
import com.douglas.thriftstore.utils.StringValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create or Update User
    public String saveUser(User user) {
        try {
            userRepository.save(user);
            return "User saved successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while saving user: " + e.getMessage();
        }
    }

    // Get User by ID
    public User getUserById(long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.orElseThrow(() -> new Exception("User not found with ID: " + id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Or you can return a custom error message
        }
    }

    // Get All Users
    public List<User> getAllUsers() {
            return userRepository.findAll();
    }

    // Delete User by ID
    public String deleteUserById(long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return "User deleted successfully";
            } else {
                return "User not found with ID: " + id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while deleting user: " + e.getMessage();
        }
    }

    // Find User by userid
    public User getUserByUserid(String userid) {
        try {
            User user = findByUserid(userid);
            if (user == null) {
                throw new Exception("User not found with userid: " + userid);
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Or you can return a custom error message
        }
    }
    
    public String authenticateUser(String userid, String password) {
        // Find user by their 'userid'
        User user = findByUserid(userid);

        // Check if user exists
        if (user == null) {
            return "User not found!";
        }

        // Compare the password with the stored password
        if (password.equals(user.getPassword())) {
            return "Authentication successful!";
        } else {
            return "Invalid password!";
        }
    }
    
    public String deactivateUser(String userid) {
       
            User user = findByUserid(userid);
            if (user == null) {
                return "User not found!";
            }
            user.setAction(false);  
            userRepository.save(user);
            return "User deactivated successfully!";
    }
    
    public String updateUser(long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirst_name(user.getFirst_name());
            existingUser.setLast_name(user.getLast_name());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setState(user.getState());
            existingUser.setAction(user.isAction());
            existingUser.setAddress(user.getAddress());
            existingUser.setNumber(user.getNumber());
            
            userRepository.save(existingUser);  // Save the updated user
            return "User updated successfully.";
        } else {
            return "User not found.";
        }
    }
    
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    
    public boolean useridExists(String userid) {
        return userRepository.findByUserid(userid) != null;
    }
    
    public User findByUserid(String userid) {
    	return userRepository.findByUserid(StringValidation.removeWhiteSpaces(userid));
    }
    
}
