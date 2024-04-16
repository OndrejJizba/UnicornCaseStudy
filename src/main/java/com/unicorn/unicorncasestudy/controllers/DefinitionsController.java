package com.unicorn.unicorncasestudy.controllers;

import com.unicorn.unicorncasestudy.models.DTOs.DefinitionsRequest;
import com.unicorn.unicorncasestudy.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefinitionsController {
    private final ProductService productService;

    @Autowired
    public DefinitionsController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/definitions")
    public ResponseEntity<?> handleDefinitions(@RequestBody DefinitionsRequest request) {
        productService.handleDefinitions(request);
        return ResponseEntity.status(200).build();
    }
}
