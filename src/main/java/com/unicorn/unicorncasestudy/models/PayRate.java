package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PayRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unit;
    private String value;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_key", referencedColumnName = "productKey")
    private Product product;

    public PayRate(String unit, String value) {
        this.unit = unit;
        this.value = value;
    }
}
