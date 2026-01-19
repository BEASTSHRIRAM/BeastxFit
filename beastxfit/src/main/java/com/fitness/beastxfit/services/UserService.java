package com.fitness.beastxfit.services;

import com.fitness.beastxfit.repository.UserRepository;
import com.fitness.beastxfit.dto.RegisterRequest;
import com.fitness.beastxfit.dto.UserResponse;
import com.fitness.beastxfit.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    public UserResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())){
            throw new RuntimeException(" Email already exist");
        }

        User user=new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword((request.getPassword()));

        User savedUser =repository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponse;
    }
    public UserResponse getUserProfile(String userId){
        User user =repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }
}
