package com.unicorn.unicorncasestudy.repositories;

import com.unicorn.unicorncasestudy.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByProductKey(String productKey);
    Product findByProductKey(String productKey);
}
