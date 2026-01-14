package com.fitness.beastxfit;

import com.fitness.beastxfit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
