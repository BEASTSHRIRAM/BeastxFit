package com.fitness.gateway.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;
    private String keyCloakId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    //whenever we push any record its value(time bro)is automatically genereation
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
