package com.unicorn.unicorncasestudy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DerivedProduct extends Product {
    public DerivedProduct(Product parentProduct) {
        super(parentProduct.getProductKey(), parentProduct.getDescription(), parentProduct.getType(), parentProduct.getRate(), parentProduct.getPayRateUnit(), parentProduct.getPayRateValue());
    }
}

