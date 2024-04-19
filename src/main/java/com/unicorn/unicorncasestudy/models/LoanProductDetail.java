package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoanProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double fixedPayment;
    private Double originalLoan;
    private Integer numberOfPayments;
    @OneToOne(mappedBy = "loanProductDetail")
    private ClientProduct clientProduct;

    public LoanProductDetail(Double fixedPayment, Double originalLoan, Integer numberOfPayments) {
        this.fixedPayment = fixedPayment;
        this.originalLoan = originalLoan;
        this.numberOfPayments = numberOfPayments;
    }
}
