package com.practica2.practica2backend.Repositories;

import com.practica2.practica2backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String  username);

}
