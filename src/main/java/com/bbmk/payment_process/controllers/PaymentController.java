package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.service.PaymentTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller class for payment related operations.
 * <p>
 */

@RestController
@RequestMapping("/api/v1/payment")
@RestControllerAdvice
@Slf4j
public class PaymentController {

    private final PaymentTransactionService transactionService;


    @Autowired
    public PaymentController(PaymentTransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/create")
    public ResponseEntity<PaymentTransaction> createTransaction(
        @RequestParam("customerId") Long customerId,
        @RequestParam("merchantId") Long merchantId,
        @RequestParam("amount") BigDecimal amount,
        @RequestParam(name = "vatRate", required = false, defaultValue = "NINETEEN_PERCENT") VatRate vatRate) {


        PaymentTransaction transaction = transactionService.createTransaction(customerId, merchantId, amount, vatRate);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    /**
     * Gets all transactions.
     *
     * @return the all transactions
     * @throws EmptyTransactionListException the empty transaction list exception
     */
    @GetMapping("/all")
    public ResponseEntity<List<PaymentTransaction>> getAllTransactions() throws EmptyTransactionListException {
        List<PaymentTransaction> transactions = transactionService.getAllTransactions();
        return transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions);
    }

    /**
     * Gets transactions by merchant.
     *
     * @param merchantId the merchant id
     * @return the transactions by merchant
     */
    @GetMapping("merchant/{merchantId}")
    public ResponseEntity<?> getTransactionsByMerchant(@PathVariable Long merchantId) {
        return Optional.ofNullable(merchantId)
            .map(transactionService::getTransactionsByMerchant)
            .map(transactions -> transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions))
            .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Gets transactions by customer.
     *
     * @param customerId the customer id
     * @return the transactions by customer
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getTransactionsByCustomer(@PathVariable Long customerId) {
        return Optional.ofNullable(customerId)
            .map(transactionService::getTransactionsByCustomer)
            .map(transactions -> transactions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactions))
            .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/deleteTransaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error occurred while deleting transaction: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
