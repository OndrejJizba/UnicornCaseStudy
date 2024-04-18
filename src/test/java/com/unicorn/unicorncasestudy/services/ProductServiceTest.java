package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.DerivedProduct;
import com.unicorn.unicorncasestudy.models.Product;
import com.unicorn.unicorncasestudy.repositories.ClientProductRepository;
import com.unicorn.unicorncasestudy.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ClientProductRepository clientProductRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImp productService;

    @BeforeEach
    void setUp() {
        clientProductRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void changeRateAndCreateDerivedProductTestAccount() {
        Product product = new Product("AAAAAA", "personal account", "ACCOUNT", 500.0, "DAY", "20");
        when(productRepository.findByProductKeyForProductType("AAAAAA")).thenReturn(product);
        productService.changeRateAndCreateDerivedProduct("AAAAAA", 300.0);
        ArgumentCaptor<DerivedProduct> captor = ArgumentCaptor.forClass(DerivedProduct.class);
        verify(productRepository).save(captor.capture());
        DerivedProduct savedProduct = captor.getValue();
        assertEquals(300.0, savedProduct.getRate());
        assertEquals(product.getType(), savedProduct.getType());
    }

    @Test
    void changeRateAndCreateDerivedProductTestLoan() {
        Product product = new Product("BBBBBB", "loan", "LOAN", 2.0, "DAY", "20");
        when(productRepository.findByProductKeyForProductType("BBBBBB")).thenReturn(product);
        productService.changeRateAndCreateDerivedProduct("BBBBBB", 2.3);
        ArgumentCaptor<DerivedProduct> captor = ArgumentCaptor.forClass(DerivedProduct.class);
        verify(productRepository).save(captor.capture());
        DerivedProduct savedProduct = captor.getValue();
        assertEquals(2.3, savedProduct.getRate());
        assertEquals(product.getType(), savedProduct.getType());
    }

    @Test
    void changeRateAndCreateDerivedProductTestAccountFailed() {
        Product product = new Product("AAAAAA", "personal account", "ACCOUNT", 500.0, "DAY", "20");
        when(productRepository.findByProductKeyForProductType("AAAAAA")).thenReturn(product);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalError = System.err;
        System.setErr(new PrintStream(output));

        productService.changeRateAndCreateDerivedProduct("AAAAAA", 100.0);

        System.setErr(originalError);
        assertTrue(output.toString().contains("The new rate does not comply with the modification rules. Product key: AAAAAA, old rate: 500.0, new rate: 100.0"));
    }

    @Test
    void changeRateAndCreateDerivedProductTestLoanFailed() {
        Product product = new Product("BBBBBB", "loan", "LOAN", 2.0, "DAY", "20");
        when(productRepository.findByProductKeyForProductType("BBBBBB")).thenReturn(product);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalError = System.err;
        System.setErr(new PrintStream(output));

        productService.changeRateAndCreateDerivedProduct("BBBBBB", 5.5);

        System.setErr(originalError);
        assertTrue(output.toString().contains("The new rate does not comply with the modification rules. Product key: BBBBBB, old rate: 2.0, new rate: 5.5"));
    }

}