package com.practica2.practica2backend.Models.Repositories;

import com.practica2.practica2backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByUsernameAndRol(String username, String rol);
}
