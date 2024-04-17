package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.ClientProduct;
import com.unicorn.unicorncasestudy.models.Product;

public interface ClientService {
    void feePayment();
    double calculatePaymentAmount(Product product);
    void updateNextPayment(ClientProduct clientProduct, String unit, int value);
}
