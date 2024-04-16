package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    @JoinColumn(name = "productKey")
    private Product product;
    private Double balance;

    public ClientProduct(Client client, Product product, Double balance) {
        this.client = client;
        this.product = product;
        this.balance = balance;
    }
}
