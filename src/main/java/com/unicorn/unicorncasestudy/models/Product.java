package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private BigDecimal rate;
    private String payRateUnit;
    private String payRateValue;
    @OneToMany(mappedBy = "product")
    private List<ClientProduct> clientProducts;
}
