package com.practica2.practica2backend.Repositories;

import com.practica2.practica2backend.Models.Mockup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MockupRepository extends JpaRepository<Mockup,String> {
    Mockup findByUuid(String uuid);
}
