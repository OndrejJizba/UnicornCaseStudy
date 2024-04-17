package com.unicorn.unicorncasestudy.models.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DefinitionsRequest {
    private List<ProductDefinitions> definitions;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ProductDefinitions {
        private String operation;
        private String productKey;
        private String description;
        private String type;
        private Double rate;
        private PayRate payRate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PayRate {
        private String unit;
        private String value;
    }
}
