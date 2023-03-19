package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import com.bbmk.payment_process.service.PaymentTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for payment related operations.
 */
@RestController
@RequestMapping("/api/v1/payment")
@RestControllerAdvice
@Slf4j
public class PaymentController {

    private final PaymentTransactionService transactionService;
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    public PaymentController(PaymentTransactionService transactionService,
                             PaymentTransactionRepository paymentTransactionRepository) {
        this.transactionService = transactionService;
        this.paymentTransactionRepository = paymentTransactionRepository;
    }


    @PostMapping("/transactions")
    public ResponseEntity<PaymentTransaction> createTransaction(@RequestBody PaymentTransaction transaction) {
        return Optional.ofNullable(transaction)
            .map(transactionService::createTransaction)
            .map(createdTransaction -> ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction))
            .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentTransaction>> getAllTransactions() throws EmptyTransactionListException {
        List<PaymentTransaction> transactions = transactionService.getAllTransactions();
        return transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions);
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<?> getTransactionsByMerchant(@PathVariable Long merchantId) {
        return Optional.ofNullable(merchantId)
            .map(transactionService::getTransactionsByMerchant)
            .map(transactions -> transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions))
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getTransactionsByCustomer(@PathVariable Long customerId) {
        return Optional.ofNullable(customerId)
            .map(transactionService::getTransactionsByCustomer)
            .map(transactions -> transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions))
            .orElse(ResponseEntity.badRequest().build());
    }


    @GetMapping("/turnover")
    public ResponseEntity<Merchant> getTotalTurnoverByMerchantAndYear(@RequestParam Integer year) {
        return Optional.ofNullable(year)
            .map(transactionService::getMerchantWithHighestTurnover)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("merchant/{Id}")
    public ResponseEntity<?> findPaymentByMerchantID(@PathVariable Long Id) {
        return Optional.ofNullable(Id)
            .map(paymentTransactionRepository::findPaymentTransactionByMerchantId)
            .map(transactions -> transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions))
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/highest-turnover")
    public ResponseEntity<Merchant> getTotalTurnoverYear(@RequestParam Integer year) {
        return Optional.ofNullable(year)
            .map(paymentTransactionRepository::getMerchantWithHighestTurnoverinYear)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

}
