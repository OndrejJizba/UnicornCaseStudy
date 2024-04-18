package com.unicorn.unicorncasestudy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicorn.unicorncasestudy.models.DTOs.DefinitionsRequest;
import com.unicorn.unicorncasestudy.models.Product;
import com.unicorn.unicorncasestudy.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class DefinitionsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        productRepository.deleteAll();
    }

    @Test
    public void handleDefinitionsTestNewProduct() throws Exception {
        int initialAmount = productRepository.findAll().size();
        DefinitionsRequest.PayRate payRate = new DefinitionsRequest.PayRate("DAY", "20");
        DefinitionsRequest.ProductDefinitions productDefinition = new DefinitionsRequest.ProductDefinitions("N", "AAAAAA", "personal account", "ACCOUNT", 300.0, payRate);
        List<DefinitionsRequest.ProductDefinitions> productList = new ArrayList<>();
        productList.add(productDefinition);
        DefinitionsRequest def = new DefinitionsRequest(productList);

        mockMvc.perform(MockMvcRequestBuilders.post("/definitions")
                .content(objectMapper.writeValueAsString(def))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        Product product = productRepository.findByProductKey("AAAAAA");
        assertEquals(product.getDescription(), "personal account");
        assertEquals(product.getType(), "ACCOUNT");
        assertEquals(product.getRate(), 300.0);
        assertEquals(product.getPayRateUnit(), "DAY");
        assertEquals(product.getPayRateValue(), "20");

        assertEquals(initialAmount + 1, productRepository.findAll().size());
    }

    @Test
    public void handleDefinitionsTestUpdateProduct() throws Exception {
        int initialAmount = productRepository.findAll().size();
        DefinitionsRequest.PayRate payRate = new DefinitionsRequest.PayRate("MONTH", "3");
        DefinitionsRequest.ProductDefinitions productDefinition = new DefinitionsRequest.ProductDefinitions("N", "AAAAAA", "personal account", "ACCOUNT", 300.0, payRate);
        DefinitionsRequest.PayRate payRate2 = new DefinitionsRequest.PayRate("DAY", "20");
        DefinitionsRequest.ProductDefinitions productDefinition2 = new DefinitionsRequest.ProductDefinitions("U", "AAAAAA", "personal account", "ACCOUNT", 150.0, payRate2);
        List<DefinitionsRequest.ProductDefinitions> productList = new ArrayList<>();
        productList.add(productDefinition);
        productList.add(productDefinition2);
        DefinitionsRequest def = new DefinitionsRequest(productList);

        mockMvc.perform(MockMvcRequestBuilders.post("/definitions")
                        .content(objectMapper.writeValueAsString(def))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        Product product = productRepository.findByProductKey("AAAAAA");
        assertEquals(product.getDescription(), "personal account");
        assertEquals(product.getType(), "ACCOUNT");
        assertEquals(product.getRate(), 150.0);
        assertEquals(product.getPayRateUnit(), "DAY");
        assertEquals(product.getPayRateValue(), "20");

        assertEquals(initialAmount + 1, productRepository.findAll().size());
    }
}