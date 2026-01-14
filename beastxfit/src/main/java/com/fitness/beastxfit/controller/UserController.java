package com.fitness.beastxfit.controller;

import com.fitness.beastxfit.dto.RegisterRequest;
import com.fitness.beastxfit.dto.UserResponse;
import com.fitness.beastxfit.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
        private UserService userService;

        @PostMapping("/register")
        public ResponseEntity<UserResponse> regiter(@RequestBody RegisterRequest request){
                return ResponseEntity.ok(userService.register(request));
        }
}

