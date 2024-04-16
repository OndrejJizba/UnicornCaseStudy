package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.Client;
import com.unicorn.unicorncasestudy.models.Product;

public interface ClientService {
    void feePayment();
    void countDaysUntilPayment(Client client, Product product);
}
