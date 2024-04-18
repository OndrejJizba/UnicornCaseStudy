package com.unicorn.unicorncasestudy.controllers;

import com.unicorn.unicorncasestudy.models.DTOs.DefinitionsRequest;
import com.unicorn.unicorncasestudy.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Handle the definitions accepted by the API", description = "This endpoint handles the definitions sent by the API. It either adds new products to the database or updates the existing ones.")
    @ApiResponse(responseCode = "200", description = "Data was accepted and processed. Error codes can be seen in the stack trace.")
    @PostMapping("/definitions")
    public ResponseEntity<?> handleDefinitions(@RequestBody DefinitionsRequest request) {
        productService.handleDefinitions(request);
        return ResponseEntity.status(200).build();
    }
}
