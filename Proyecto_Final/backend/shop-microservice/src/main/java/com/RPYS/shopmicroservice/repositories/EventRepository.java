package com.RPYS.shopmicroservice.repositories;

import com.RPYS.shopmicroservice.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {

}
