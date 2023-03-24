package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.repositories.MerchantRepository;
import com.bbmk.payment_process.requests.TransferMoneyRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

@Service
public class MoneyTransferService {

    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionTemplate transactionTemplate;

    public MoneyTransferService(CustomerRepository customerRepository,
                                MerchantRepository merchantRepository, TransactionTemplate transactionTemplate) {
        this.customerRepository = customerRepository;
        this.merchantRepository = merchantRepository;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public void transferMoney(TransferMoneyRequest request) {
        transactionTemplate.execute(transactionStatus -> {
            Optional<Customer> customerOpt = customerRepository.findById(request.getCustomerId());
            Optional<Merchant> merchantOpt = merchantRepository.findById(request.getMerchantId());

            if (customerOpt.isEmpty() || merchantOpt.isEmpty()) {
                throw new IllegalArgumentException("Customer or merchant not found");
            }

            Customer customer = customerOpt.get();
            Merchant merchant = merchantOpt.get();

            if (customer.getBalance().compareTo(request.getAmount()) < 0) {
                throw new IllegalStateException("Insufficient balance for the customer");
            }

            customer.setBalance(customer.getBalance().subtract(request.getAmount()));
            merchant.setBalance(merchant.getBalance().add(request.getAmount()));

            customerRepository.save(customer);
            merchantRepository.save(merchant);

            return null;
        });
    }
}
