package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String payRateUnit;
    private String payRateValue;
    @OneToMany(mappedBy = "product")
    private List<ClientProduct> clientProducts;

    public Product(String productKey, String description, String type, Double rate, String payRateUnit, String payRateValue) {
        this.productKey = productKey;
        this.description = description;
        this.type = type;
        this.rate = rate;
        this.payRateUnit = payRateUnit;
        this.payRateValue = payRateValue;
    }
}
