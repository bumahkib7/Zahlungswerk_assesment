package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.service.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for payment related operations.
 */
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentTransactionService transactionService;

    @Autowired
    public PaymentController(PaymentTransactionService transactionService) {
        this.transactionService = transactionService;
    }


    /**
     * Creates a new payment transaction.
     *
     * @param transaction The payment transaction to be created.
     * @return The created payment transaction.
     */
    @PostMapping("/transactions")
    public ResponseEntity<PaymentTransaction> createTransaction(@RequestBody PaymentTransaction transaction) {
        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            PaymentTransaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Gets all payment transactions.
     *
     * @return A list of all payment transactions.
     */
    @GetMapping("/all")
    public ResponseEntity<List<PaymentTransaction>> getAllTransactions() {
        try {
            List<PaymentTransaction> transactions = transactionService.getAllTransactions();
            if (transactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactions);
        } catch (EmptyTransactionListException e) {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Gets all payment transactions for a given merchant.
     *
     * @param merchantId The ID of the merchant.
     * @return A list of all payment transactions for the given merchant.
     */
    @GetMapping("/{merchantId}")
    public ResponseEntity<List<PaymentTransaction>> getTransactionsByMerchant(@PathVariable Long merchantId) {
        if (merchantId == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            List<PaymentTransaction> transactions = transactionService.getTransactionsByMerchant(merchantId);
            if (transactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Gets all payment transactions for a given customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of all payment transactions for the given customer.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentTransaction>> getTransactionsByCustomer(@PathVariable Long customerId) {
        if (customerId == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            List<PaymentTransaction> transactions = transactionService.getTransactionsByCustomer(customerId);
            if (transactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/turnover")
    public ResponseEntity<Merchant> getTotalTurnoverByMerchantAndYear(@RequestParam Integer year) {
        if (year == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Merchant totalTurnover = transactionService.getMerchantWithHighestTurnover(year);
            return ResponseEntity.ok(totalTurnover);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
