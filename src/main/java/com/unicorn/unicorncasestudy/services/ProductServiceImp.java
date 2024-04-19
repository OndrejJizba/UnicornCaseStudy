package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.DTOs.DefinitionsRequest;
import com.unicorn.unicorncasestudy.models.DerivedProduct;
import com.unicorn.unicorncasestudy.models.Product;
import com.unicorn.unicorncasestudy.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public void handleDefinitions(DefinitionsRequest request) {
        List<String> errors = new ArrayList<>();
        try {
            for (DefinitionsRequest.ProductDefinitions def : request.getDefinitions()) {
                if (def.getOperation() == null) {
                    errors.add("Missing operation type for product with key " + def.getProductKey());
                } else if (def.getOperation().equals("N")) {
                    if (def.getProductKey() == null) errors.add("New - Missing product key.");
                    else if (def.getDescription() == null || def.getType() == null || def.getRate() == null || def.getPayRate() == null || def.getPayRate().getUnit() == null || def.getPayRate().getValue() == null) {
                        errors.add("New - Incomplete data for product with key " + def.getProductKey());
                    }
                    else handleNewProductDefinition(def, errors);
                } else if (def.getOperation().equals("U")) {
                    if (def.getProductKey() == null) errors.add("Update - Missing product key.");
                    else if (def.getRate() == null || def.getPayRate() == null ||def.getPayRate().getUnit() == null || def.getPayRate().getValue() == null) {
                        errors.add("Update - Incomplete data for product with key " + def.getProductKey());
                    }
                    else handleUpdatedProductDefinition(def, errors);
                }
            }
        } catch (IllegalArgumentException e) {
            errors.add(e.getMessage());
        }
        printErrors(errors);
    }

    private void handleNewProductDefinition(DefinitionsRequest.ProductDefinitions def, List<String> errors) {
        if (!productRepository.existsByProductKey(def.getProductKey())) {
            saveProductFromDefinition(def);
        } else {
            errors.add("New - Product with key " + def.getProductKey() + " already exists.");
        }
    }

    private void handleUpdatedProductDefinition(DefinitionsRequest.ProductDefinitions def, List<String> errors) {
        if (productRepository.existsByProductKey(def.getProductKey())) {
            Product product = productRepository.findByProductKeyForProductType(def.getProductKey());
            updateProductFromDefinition(product, def);
            productRepository.save(product);
            updateDerivedProducts(def);
        } else {
            errors.add("Update - Product with key " + def.getProductKey() + " does not exist.");
        }
    }

    private void saveProductFromDefinition(DefinitionsRequest.ProductDefinitions def) {
        Product product = new Product();
        product.setProductKey(def.getProductKey());
        product.setDescription(def.getDescription());
        product.setType(def.getType());
        product.setRate(def.getRate());
        product.setPayRateUnit(def.getPayRate().getUnit());
        product.setPayRateValue(def.getPayRate().getValue());
        productRepository.save(product);
    }

    private void updateProductFromDefinition(Product product, DefinitionsRequest.ProductDefinitions def) {
        product.setRate(def.getRate());
        product.setPayRateUnit(def.getPayRate().getUnit());
        product.setPayRateValue(def.getPayRate().getValue());
    }

    private void updateDerivedProducts(DefinitionsRequest.ProductDefinitions def) {
        Product mainProduct = productRepository.findByProductKeyForProductType(def.getProductKey());
        double changeRate = def.getRate() - mainProduct.getRate();
        List<Product> derivedProducts = productRepository.findByProductKeyAndDTypeDerived(def.getProductKey());
        for (Product p : derivedProducts) {
            p.setPayRateUnit(def.getPayRate().getUnit());
            p.setPayRateValue(def.getPayRate().getValue());
            double newRate = p.getRate() + changeRate;
            p.setRate(newRate);
            productRepository.save(p);
        }
    }

    private void printErrors(List<String> errors) {
        for (String error : errors) {
            System.err.println(error);
        }
    }

    @Override
    public void changeRateAndCreateDerivedProduct(String productKey, Double newRate) {
        Product product = productRepository.findByProductKeyForProductType(productKey);
        DerivedProduct derivedProduct = new DerivedProduct(product);
        List<String> errors = new ArrayList<>();
        try {
            if (product.getType().equals("ACCOUNT")) {
                if (newRate >= 0 && newRate >= product.getRate() - 250 && newRate <= product.getRate() + 250) {
                    derivedProduct.setRate(newRate);
                    productRepository.save(derivedProduct);
                } else errors.add("The new rate does not comply with the modification rules. Product key: " + productKey + ", old rate: " + product.getRate() + ", new rate: " + newRate);
            } else if (product.getType().equals("LOAN")) {
                if (newRate >= 0 && newRate >= product.getRate() * 0.8 && newRate <= product.getRate() * 1.2) {
                    derivedProduct.setRate(newRate);
                    productRepository.save(derivedProduct);
                } else errors.add("The new rate does not comply with the modification rules. Product key: " + productKey + ", old rate: " + product.getRate() + ", new rate: " + newRate);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        for (String error : errors) {
            System.err.println(error);
        }
    }
}
