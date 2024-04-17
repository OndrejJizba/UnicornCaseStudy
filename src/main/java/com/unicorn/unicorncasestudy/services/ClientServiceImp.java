package com.unicorn.unicorncasestudy.services;

import com.unicorn.unicorncasestudy.models.ClientProduct;
import com.unicorn.unicorncasestudy.models.Product;
import com.unicorn.unicorncasestudy.repositories.ClientProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        List<ClientProduct> todayPayments = clientProductRepository.findByNextPayment(LocalDate.now());
        for (ClientProduct clientProduct : todayPayments) {
            Product product = clientProduct.getProduct();
            double amountToPay = calculatePaymentAmount(product);
            clientProduct.setBalance(clientProduct.getBalance() - amountToPay);
            updateNextPayment(clientProduct, product.getPayRateUnit(), Integer.parseInt(product.getPayRateValue()));
            clientProductRepository.save(clientProduct);
        }
    }

    @Override
    public double calculatePaymentAmount(Product product) {
        if (product.getType().equals("ACCOUNT")) return product.getRate();
        else if (product.getType().equals("LOAN")) {
            double fixedPayment = 100;
            double originalLoan = 2000;
            int numberOfPayments = 10;
            return fixedPayment + (originalLoan * product.getRate()) / numberOfPayments;
        }
        return 0;
    }

    @Override
    public void updateNextPayment(ClientProduct clientProduct, String unit, int value) {
        LocalDate nextPayment;
        if (unit.equals("DAY")) nextPayment = LocalDate.now().plusDays(value);
        else if (unit.equals("MONTH")) nextPayment = LocalDate.now().plusDays(value * 30L);
        else nextPayment = LocalDate.now();

        clientProduct.setNextPayment(nextPayment);
    }
}
