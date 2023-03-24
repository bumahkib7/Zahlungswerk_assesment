package com.bbmk.payment_process.service;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.repositories.MerchantRepository;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import com.bbmk.payment_process.requests.TransferMoneyRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class PaymentTransactionService {
    private final PaymentTransactionRepository transactionRepository;

    private final CustomerRepository customerRepository;

    private final MerchantRepository merchantRepository;

    private final MoneyTransferService moneyTransferService;

    @Autowired
    public PaymentTransactionService(PaymentTransactionRepository transactionRepository,
                                     MerchantRepository merchantRepository,
                                     CustomerRepository customerRepository,
                                     MoneyTransferService moneyTransferService) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
        this.customerRepository = customerRepository;
        this.moneyTransferService = moneyTransferService;
    }

    @Transactional
    public PaymentTransaction createTransaction(Long customerId, Long merchantId, BigDecimal amount, VatRate vatRate) {
        // Find the customer and merchant by their respective IDs
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
        Merchant merchant = merchantRepository.findById(merchantId)
            .orElseThrow(() -> new EntityNotFoundException("Merchant not found with ID: " + merchantId));

        // Create a new transaction with the given amount
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setGrossAmount(amount);
        transaction.setTransactionDate(ZonedDateTime.now()); // Set the current date and time
        transaction.setCustomer(customer);
        transaction.setMerchant(merchant);
        transaction.setVatRate(vatRate);

        String receiptId = generateRandomReceiptId();
        transaction.setReceiptId(receiptId);

        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (transactionRepository.existsById(uuid));
        transaction.setId(uuid);

        PaymentTransaction savedTransaction = transactionRepository.save(transaction);

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest();
        transferMoneyRequest.setCustomerId(customerId);
        transferMoneyRequest.setMerchantId(merchantId);
        transferMoneyRequest.setAmount(amount);
        moneyTransferService.transferMoney(transferMoneyRequest);

        return savedTransaction;
    }

    private String generateRandomReceiptId() {
        int length = 10;
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder receiptId = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(alphanumeric.length());
            receiptId.append(alphanumeric.charAt(index));
        }

        return receiptId.toString();
    }


    public List<PaymentTransaction> getAllTransactions() throws EmptyTransactionListException {
        List<PaymentTransaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new EmptyTransactionListException("Transaction list is empty");
        }
        return transactions;
    }

    public List<PaymentTransaction> getTransactionsByMerchant(Long merchantId) throws IllegalArgumentException {
        if (merchantId == null) {
            throw new NullPointerException("Merchant ID cannot be null");
        }
        return transactionRepository.findByMerchantId(merchantId);
    }

    public List<PaymentTransaction> getTransactionsByCustomer(Long customerId) throws IllegalArgumentException {
        if (customerId == null) {
            throw new NullPointerException("Customer ID cannot be null");
        }
        return transactionRepository.findByCustomerId(customerId);
    }


    public void deleteTransaction(UUID id) {
        try {
            transactionRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error occurred while deleting transaction: " + e.getMessage());
            throw e;
        }
    }

    public Optional<PaymentTransaction> getTransactionById(long id) {
        try {
            return transactionRepository.findById(id);
        } catch (Exception e) {
            log.error("Error occurred while getting transaction by id: " + e.getMessage());
            throw e;
        }
    }
}
