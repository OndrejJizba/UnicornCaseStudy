package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.DTOs.DefinitionsRequest;
import com.unicorn.unicorncasestudy.models.DerivedProduct;
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

    @Override
    public void changeRateAndCreateDerivedProduct(String productKey, Double newRate) {
        Product product = productRepository.findByProductKeyForProductType(productKey);
        DerivedProduct derivedProduct = new DerivedProduct(product);
        try {
            if (product.getType().equals("ACCOUNT")) {
                if (newRate >= 0 && newRate >= product.getRate() - 250 && newRate <= product.getRate() + 250) derivedProduct.setRate(newRate);
                else throw new IllegalArgumentException("The new rate does not comply with the modification rules.");
            } else if (product.getType().equals("LOAN")) {
                if (newRate >= 0 && newRate >= product.getRate() * 0.8 && newRate <= product.getRate() * 1.2) derivedProduct.setRate(newRate);
                else throw new IllegalArgumentException("The new rate does not comply with the modification rules.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        productRepository.save(derivedProduct);
    }
}
