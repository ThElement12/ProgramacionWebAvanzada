package com.RPYS.shopmicroservice.controllers;

import com.RPYS.shopmicroservice.entities.Event;
import com.RPYS.shopmicroservice.entities.Plan;
import com.RPYS.shopmicroservice.services.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
//@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/plan/")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Plan> findAll() {
        return planService.findAll();
    }


}
