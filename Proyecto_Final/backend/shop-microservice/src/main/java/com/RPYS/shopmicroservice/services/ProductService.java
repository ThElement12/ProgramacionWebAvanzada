package com.RPYS.shopmicroservice.services;

import com.RPYS.shopmicroservice.entities.Product;
import com.RPYS.shopmicroservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).get();
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }


}
