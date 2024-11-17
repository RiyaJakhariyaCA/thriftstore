package com.douglas.thriftstore.service;

import com.douglas.thriftstore.model.User;
import com.douglas.thriftstore.repository.UserRepository;
import com.douglas.thriftstore.utils.StringValidation;

import co.douglas.thriftstore.dto.NormalResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create or Update User
    public NormalResponse saveUser(User user) {
        try {
            userRepository.save(user);
            return new NormalResponse("User saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new NormalResponse("Error while saving user: ");
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
    public NormalResponse deleteUserById(long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return new NormalResponse("User deleted successfully");
            } else {
                return new NormalResponse("User not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new NormalResponse("Error while deleting user: " + e.getMessage());
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
    
    public NormalResponse authenticateUser(String userid, String password) {
        // Find user by their 'userid'
        User user = findByUserid(userid);

        // Check if user exists
        if (user == null) {
            return new NormalResponse("User not found!");
        }

        // Compare the password with the stored password
        if (password.equals(user.getPassword())) {
            return new NormalResponse("Authentication successful!");
        } else {
            return new NormalResponse("Invalid password!");
        }
    }
    
    public NormalResponse deactivateUser(String userid) {
       
            User user = findByUserid(userid);
            if (user == null) {
                return new NormalResponse("User not found!");
            }
            user.setAction(false);  
            userRepository.save(user);
            return new NormalResponse("User deactivated successfully!");
    }
    
    public NormalResponse updateUser(long id, User user) {
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
            return new NormalResponse("User updated successfully.");
        } else {
            return new NormalResponse("User not found.");
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
