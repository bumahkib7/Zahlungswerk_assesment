package com.bbmk.payment_process.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import com.bbmk.payment_process.service.PaymentTransactionService;

import java.math.BigDecimal;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class PaymentControllerTest {
    /**
     * Method under test: {@link PaymentController#createTransaction(PaymentTransaction)}
     */
    @Test
    void testCreateTransaction() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        PaymentController paymentController = new PaymentController(new PaymentTransactionService(transactionRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))));
        PaymentTransaction transaction = null;

        // Act
        ResponseEntity<PaymentTransaction> actualCreateTransactionResult = paymentController.createTransaction(transaction);

        // Assert
        assertNull(actualCreateTransactionResult.getBody());
        assertEquals(400, actualCreateTransactionResult.getStatusCodeValue());
        assertTrue(actualCreateTransactionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link PaymentController#createTransaction(PaymentTransaction)}
     */
    @Test
    void testCreateTransaction2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        PaymentController paymentController = new PaymentController(new PaymentTransactionService(transactionRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))));

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);
        paymentTransaction.setMerchant(null);
        paymentTransaction.setCustomer(null);

        // Act
        ResponseEntity<PaymentTransaction> actualCreateTransactionResult = paymentController
            .createTransaction(paymentTransaction);

        // Assert
        assertNull(actualCreateTransactionResult.getBody());
        assertEquals(400, actualCreateTransactionResult.getStatusCodeValue());
        assertTrue(actualCreateTransactionResult.getHeaders().isEmpty());
    }
}

