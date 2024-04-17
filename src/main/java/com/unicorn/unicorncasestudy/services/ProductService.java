package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.DTOs.DefinitionsRequest;

public interface ProductService {
    void handleDefinitions(DefinitionsRequest request);
    void changeRateAndCreateDerivedProduct(String productKey, Double newRate);
}
