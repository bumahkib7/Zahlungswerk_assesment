package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.service.CustomerService;
import com.bbmk.payment_process.service.MerchantService;
import com.bbmk.payment_process.service.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentTransactionService transactionService;



    @Autowired
    public PaymentController(PaymentTransactionService transactionService, MerchantService merchantService, CustomerService customerService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<PaymentTransaction> createTransaction(@RequestBody PaymentTransaction transaction) {
        try {
            PaymentTransaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PaymentTransaction>> getAllTransactions() {
        try {
            List<PaymentTransaction> transactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(transactions);
        } catch (EmptyTransactionListException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<List<PaymentTransaction>> getTransactionsByMerchant(@PathVariable Long merchantId) {
        try {
            List<PaymentTransaction> transactions = transactionService.getTransactionsByMerchant(merchantId);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException | EmptyTransactionListException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<PaymentTransaction>> getTransactionsByCustomer(@PathVariable Long customerId) {
        try {
            List<PaymentTransaction> transactions = transactionService.getTransactionsByCustomer(customerId);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException | EmptyTransactionListException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/turnover")
    public ResponseEntity<Merchant> getTotalTurnoverByMerchantAndYear(@RequestParam Integer year) {
        try {
            Merchant totalTurnover = transactionService.getMerchantWithHighestTurnover(year);
            return ResponseEntity.ok(totalTurnover);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
