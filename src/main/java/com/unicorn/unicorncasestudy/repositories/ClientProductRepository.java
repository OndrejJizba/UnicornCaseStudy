package com.unicorn.unicorncasestudy.repositories;

import com.unicorn.unicorncasestudy.models.ClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
    List<ClientProduct> findByNextPayment(LocalDate today);
}
