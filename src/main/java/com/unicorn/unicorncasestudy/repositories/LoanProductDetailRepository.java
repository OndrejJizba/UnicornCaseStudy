package com.unicorn.unicorncasestudy.repositories;

import com.unicorn.unicorncasestudy.models.LoanProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanProductDetailRepository extends JpaRepository<LoanProductDetail, Long> {
}
