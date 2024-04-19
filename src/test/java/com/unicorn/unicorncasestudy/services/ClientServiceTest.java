package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.Client;
import com.unicorn.unicorncasestudy.models.ClientProduct;
import com.unicorn.unicorncasestudy.models.Product;
import com.unicorn.unicorncasestudy.repositories.ClientProductRepository;
import com.unicorn.unicorncasestudy.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientProductRepository clientProductRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ClientServiceImp clientService;

    @BeforeEach
    void setUp() {
        clientProductRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void feePaymentTest() {
        LocalDate today = LocalDate.now();
        Product product = new Product("AAAAAA", "personal account", "ACCOUNT", 300.0, "DAY", "20");
        ClientProduct clientProduct = new ClientProduct(new Client(), product, 1000.0, 100.0, 500.0, 5);
        clientProduct.setNextPayment(today);
        List<ClientProduct> todayPayments = List.of(clientProduct);
        when(clientProductRepository.findByNextPayment(today)).thenReturn(todayPayments);
        clientService.feePayment();
        assertEquals(700.0, clientProduct.getBalance());
        assertEquals(today.plusDays(20), clientProduct.getNextPayment());
    }
}