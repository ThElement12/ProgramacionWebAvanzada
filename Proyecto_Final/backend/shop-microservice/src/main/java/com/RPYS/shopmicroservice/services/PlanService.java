package com.RPYS.shopmicroservice.services;

import com.RPYS.shopmicroservice.entities.Plan;
import com.RPYS.shopmicroservice.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan save(Plan plan){
        return planRepository.save(plan);
    }

    public List<Plan> findAll(){
        return planRepository.findAll();
    }

    public Plan findById(Integer id) {
        return planRepository.findById(id).get();
    }

    public void delete(Plan plan) {
        planRepository.delete(plan);
    }
    
}
