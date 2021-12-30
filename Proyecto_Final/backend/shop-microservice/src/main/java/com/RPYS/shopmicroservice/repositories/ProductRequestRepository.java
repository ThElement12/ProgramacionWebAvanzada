package com.RPYS.shopmicroservice.repositories;

import com.RPYS.shopmicroservice.entities.Product;
import com.RPYS.shopmicroservice.entities.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest,String> {
}
