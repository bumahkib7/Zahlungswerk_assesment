package com.bbmk.payment_process.service;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
        try {
            log.info("Checking for null values ");
            if (transaction == null || transaction.getCustomer() == null || transaction.getMerchant() == null) {
                log.warn("Provide values for Transaction to complete");
                throw new IllegalArgumentException("Merchant is invalid");
            }
            log.info("Now checking if transaction exists");
            PaymentTransaction existingTransaction = transactionRepository.findCustomerAndMerchantById(transaction.getCustomer().getId(), transaction.getMerchant().getId());
            if (existingTransaction != null) {
                log.warn("Transaction with id " + transaction.getCustomer().getId() + " already exists");
                throw new IllegalArgumentException("Transaction already exists");
            }
            log.info("Creating new transaction with id " + transaction.getCustomer().getId());
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            log.error("Error occurred while creating transaction: " + e.getMessage());
            throw e;
        }
    }

    public List<PaymentTransaction> getAllTransactions() throws EmptyTransactionListException {
        try {
            List<PaymentTransaction> transactions = transactionRepository.findAll();
            if (transactions.isEmpty()) {
                throw new EmptyTransactionListException("Transaction list is empty");
            }
            return transactions;
        } catch (Exception e) {
            log.error("Error occurred while getting all transactions: " + e.getMessage());
            throw e;
        }
    }

    public List<PaymentTransaction> getTransactionsByMerchant(Long merchantId) throws IllegalArgumentException {
        try {
            if (merchantId == null) {
                throw new NullPointerException("Merchant ID cannot be null");
            }
            return transactionRepository.findByMerchantId(merchantId);
        } catch (Exception e) {
            log.error("Error occurred while getting transactions by merchant: " + e.getMessage());
            throw e;
        }
    }

    public List<PaymentTransaction> getTransactionsByCustomer(Long customerId) throws IllegalArgumentException {
        try {
            if (customerId == null) {
                throw new NullPointerException("Customer ID cannot be null");
            }
            return transactionRepository.findByCustomerId(customerId);
        } catch (Exception e) {
            log.error("Error occurred while getting transactions by customer: " + e.getMessage());
            throw e;
        }
    }

    public Merchant getMerchantWithHighestTurnover(int year) throws NonUniqueResultException {
        try {
            TypedQuery<Merchant> query = entityManager.createQuery(
                "SELECT m FROM Merchant m JOIN m.transactions pt WHERE YEAR(pt.transactionDate) = :year AND pt.grossAmount > 0 GROUP BY m.id ORDER BY SUM(pt.grossAmount) DESC", Merchant.class);
            query.setParameter("year", year);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
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

    public Optional<PaymentTransaction> getTransactionById(long l) {
        try {
            return transactionRepository.findById(l);
        } catch (Exception e) {
            log.error("Error occurred while getting transaction by id: " + e.getMessage());
            throw e;
        }
    }

    public PaymentTransaction updateTransaction(long id, PaymentTransaction updatedTransaction) {
        try {
            if (entityManager == null) {
                return null;
            }
            String hql = "UPDATE PaymentTransaction t " +
                "SET t.transactionDate = :transactionDate, " +
                "t.grossAmount = :grossAmount, " +
                "t.vatRate = :VATRate, " +
                "t.receiptId = :receiptId " +
                "WHERE t.id = :id";
            TypedQuery<PaymentTransaction> query = entityManager.createQuery(hql, PaymentTransaction.class);
            query.setParameter("transactionDate", updatedTransaction.getTransactionDate());
            query.setParameter("grossAmount", updatedTransaction.getGrossAmount());
            query.setParameter("VATRate", updatedTransaction.getVatRate());
            query.setParameter("receiptId", updatedTransaction.getReceiptId());
            query.setParameter("id", id);
            int rowsUpdated = query.executeUpdate();
            if (rowsUpdated == 0) {
                return null;
            }
            return entityManager.find(PaymentTransaction.class, id);
        } catch (Exception e) {
            log.error("Error occurred while updating transaction: " + e.getMessage());
            throw e;
        }
    }


}
