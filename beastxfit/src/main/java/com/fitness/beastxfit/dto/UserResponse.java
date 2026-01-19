package com.fitness.beastxfit.dto;

import com.fitness.beastxfit.model.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    //whenever we push any record its value(time bro)is automatically genereation
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
