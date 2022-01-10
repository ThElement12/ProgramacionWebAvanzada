package com.RPYS.shopmicroservice.controllers;

import com.RPYS.shopmicroservice.entities.Plan;
import com.RPYS.shopmicroservice.entities.Product;
import com.RPYS.shopmicroservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/product/")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Product> findAll() {
        return productService.findAll();
    }
}
