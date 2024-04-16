package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.Client;
import com.unicorn.unicorncasestudy.models.ClientProduct;
import com.unicorn.unicorncasestudy.models.Product;
import com.unicorn.unicorncasestudy.repositories.ClientProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImp implements ClientService {
    private final ClientProductRepository clientProductRepository;

    @Autowired
    public ClientServiceImp(ClientProductRepository clientProductRepository) {
        this.clientProductRepository = clientProductRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void feePayment() {
        List<ClientProduct> clientProducts = clientProductRepository.findAll();
        for (ClientProduct clientProduct : clientProducts) {
            clientProduct.setDaysUntilPayment(clientProduct.getDaysUntilPayment() - 1);
            if (clientProduct.getDaysUntilPayment() <= 0) {
                Product product = clientProduct.getProduct();
                double amountToPay = 0;
                if (product.getType().equals("ACCOUNT")) {
                    amountToPay = product.getRate();
                } else if (product.getType().equals("LOAN")) {
                    double fixedPayment = 100;
                    double originalLoan = 2000;
                    int numberOfPayments = 10;
                    amountToPay = fixedPayment + (originalLoan * product.getRate()) / numberOfPayments;
                }
                clientProduct.setBalance(clientProduct.getBalance() - amountToPay);
                clientProductRepository.save(clientProduct);
            }
        }
    }

    @Override
    public void countDaysUntilPayment(Client client, Product product) {
        Optional<ClientProduct> clientProduct = clientProductRepository.findById(client.getId());
        int payRate = Integer.parseInt(product.getPayRate().getValue());
        if (product.getPayRate().getUnit().equals("DAY")) {
            clientProduct.get().setDaysUntilPayment(payRate);
        } else if (product.getPayRate().getUnit().equals("MONTH")) {
            clientProduct.get().setDaysUntilPayment(payRate * 30);
        } else {
            throw new IllegalArgumentException("Wrong unit type: " + product.getPayRate().getUnit());
        }
    }
}
