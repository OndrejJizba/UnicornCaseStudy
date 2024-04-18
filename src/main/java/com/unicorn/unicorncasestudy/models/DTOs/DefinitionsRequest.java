package com.unicorn.unicorncasestudy.models.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Structure of requests sent to the API.")
public class DefinitionsRequest {
    private List<ProductDefinitions> definitions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDefinitions {
        @Schema(description = "Type of operation - 'N' for new, 'U' for update", example = "N")
        private String operation;
        @Schema(description = "Unique 6-character code key", example = "ABC123")
        private String productKey;
        @Schema(description = "Description of the product", example = "personal account")
        private String description;
        @Schema(description = "Type of product - 'ACCOUNT' or 'LOAN'", example = "ACCOUNT")
        private String type;
        @Schema(description = "Rate of the product", example = "300.0")
        private Double rate;
        private PayRate payRate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayRate {
        @Schema(description = "Unit of the pay rate - 'DAY' or 'MONTH'", example = "DAY")
        private String unit;
        @Schema(description = "Value of the pay rate", example = "20")
        private String value;
    }
}
