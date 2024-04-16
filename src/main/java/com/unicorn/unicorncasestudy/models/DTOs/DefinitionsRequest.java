package com.unicorn.unicorncasestudy.models.DTOs;

import com.unicorn.unicorncasestudy.models.PayRate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DefinitionsRequest {
    public static class ProductDefinitions {
        private String operation;
        private String productKey;
        private String description;
        private String type;
        private Double rate;
        private PayRate payRate;
    }

    public static class PayRate {
        private String unit;
        private String value;
    }
}
