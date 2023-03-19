package com.bbmk.payment_process.service;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentTransactionService {
    private final PaymentTransactionRepository transactionRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public PaymentTransactionService(PaymentTransactionRepository transactionRepository, EntityManager entityManager) {
        this.transactionRepository = transactionRepository;
        this.entityManager = entityManager;
    }

   public PaymentTransaction createTransaction(PaymentTransaction transaction) throws IllegalArgumentException {
    if (transaction == null || transaction.getCustomer() == null || transaction.getMerchant() == null) {
        throw new IllegalArgumentException("Transaction, customer, and merchant must be provided");
    }

    Optional<PaymentTransaction> existingTransaction = Optional.ofNullable(transactionRepository.findCustomerAndMerchantById(transaction.getCustomer().getId(),
        transaction.getMerchant().getId()));
    if (existingTransaction.isPresent()) {
        throw new IllegalArgumentException("Transaction with id " + transaction.getCustomer().getId() + " already exists");
    }

    return transactionRepository.save(transaction);
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

    public Merchant getMerchantWithHighestTurnover(int year) throws NonUniqueResultException {
        try {
            return entityManager.createQuery(
                    "SELECT m FROM Merchant m JOIN m.transactions pt WHERE YEAR(pt.transactionDate) = :year AND pt.grossAmount > 0 GROUP BY m.id ORDER BY SUM(pt.grossAmount) DESC", Merchant.class)
                .setParameter("year", year)
                .setMaxResults(1)
                .getSingleResult();
        } catch (NonUniqueResultException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while getting merchant with highest turnover: " + e.getMessage());
            throw e;
        }
    }

    public void deleteTransaction(long id) {
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
