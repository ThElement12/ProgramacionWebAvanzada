package com.RPYS.shopmicroservice.repositories;

import com.RPYS.shopmicroservice.entities.Event;
import com.RPYS.shopmicroservice.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan,Integer> {
}
