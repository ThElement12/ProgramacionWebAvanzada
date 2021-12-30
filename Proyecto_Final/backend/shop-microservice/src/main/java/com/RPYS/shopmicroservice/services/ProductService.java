package com.RPYS.shopmicroservice.services;

import com.RPYS.shopmicroservice.entities.Product;
import com.RPYS.shopmicroservice.entities.ProductRequest;
import com.RPYS.shopmicroservice.repositories.ProductRepository;
import com.RPYS.shopmicroservice.repositories.ProductRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductRequestRepository productRequestRepository;

    public ProductService(ProductRepository productRepository, ProductRequestRepository productRequestRepository) {
        this.productRepository = productRepository;
        this.productRequestRepository = productRequestRepository;
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public ProductRequest saveProductRequest(ProductRequest request){
        return productRequestRepository.save(request);
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
