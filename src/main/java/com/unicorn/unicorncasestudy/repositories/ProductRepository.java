package com.unicorn.unicorncasestudy.repositories;

import com.unicorn.unicorncasestudy.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByProductKey(String productKey);
    Product findByProductKey(String productKey);
    @Query(value = "SELECT * FROM Product p WHERE p.product_key = :productKey AND p.dType = 'Product'", nativeQuery = true)
    Product findByProductKeyForProductType(@Param("productKey") String productKey);
}
