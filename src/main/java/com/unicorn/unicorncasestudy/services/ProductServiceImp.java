package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.DTOs.DefinitionsRequest;
import com.unicorn.unicorncasestudy.models.Product;
import com.unicorn.unicorncasestudy.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void handleDefinitions(DefinitionsRequest request) {
        for (DefinitionsRequest.ProductDefinitions def : request.getDefinitions()) {
            if (def.getOperation().equals("N")) {
                Product product = new Product();
                product.setProductKey(def.getProductKey());
                product.setDescription(def.getDescription());
                product.setType(def.getType());
                product.setRate(def.getRate());
                product.setPayRateUnit(def.getPayRate().getUnit());
                product.setPayRateValue(def.getPayRate().getValue());
                productRepository.save(product);
            } else if (def.getOperation().equals("U")) {
                if (productRepository.existsByProductKey(def.getProductKey())) {
                    Product product = productRepository.findByProductKey(def.getProductKey());
                    product.setRate(def.getRate());
                    product.setPayRateUnit(def.getPayRate().getUnit());
                    product.setPayRateValue(def.getPayRate().getValue());
                    productRepository.save(product);
                } else {
                    throw new IllegalArgumentException("Product with key " + def.getProductKey() + " does not exist.");
                }
            }
        }
    }
}
