package com.example.zahlungswerk.service;

import com.example.zahlungswerk.models.PaymentTransaction;
import com.example.zahlungswerk.repositories.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository transactionRepository;

    public PaymentTransactionService(PaymentTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public PaymentTransaction createTransaction(PaymentTransaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<PaymentTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<PaymentTransaction> getTransactionsByMerchant(Long merchantId) {
        return transactionRepository.findByMerchantId(merchantId);
    }

    public List<PaymentTransaction> getTransactionsByCustomer(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    public BigDecimal getTotalTurnoverByMerchantAndYear(Long merchantId, Integer year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<PaymentTransaction> transactions = transactionRepository.findByMerchantIdAndTransactionDateBetween(merchantId, startDate, endDate);
        return transactions.stream().map(PaymentTransaction::getGrossAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
