package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    private String productKey;
    private String description;
    private String type;
    private Double rate;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private PayRate payRate;
    @OneToMany(mappedBy = "product")
    private List<ClientProduct> clientProducts;

    public Product(String productKey, String description, String type, Double rate, PayRate payRate) {
        this.productKey = productKey;
        this.description = description;
        this.type = type;
        this.rate = rate;
        this.payRate = payRate;
    }

    public Product(String productKey, String description, String type, Double rate) {
        this.productKey = productKey;
        this.description = description;
        this.type = type;
        this.rate = rate;
    }
}
