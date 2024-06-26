package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClientProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Double balance;
    private LocalDate createdAt;
    private LocalDate nextPayment;
    @OneToOne
    @JoinColumn(name = "loan_product_detail_id")
    private LoanProductDetail loanProductDetail;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDate.now();
        String unit = product.getPayRateUnit();
        int value = Integer.parseInt(product.getPayRateValue());
        if (unit.equals("DAY")) nextPayment = createdAt.plusDays(value);
        else if (unit.equals("MONTH")) nextPayment = createdAt.plusDays(value * 30L);
    }
    public ClientProduct(Client client, Product product, Double balance) {
        this.client = client;
        this.product = product;
        this.balance = balance;
    }
    public ClientProduct(Client client, Product product, Double balance, LoanProductDetail loanProductDetail) {
        this.client = client;
        this.product = product;
        this.balance = balance;
        this.loanProductDetail = loanProductDetail;
    }
}
