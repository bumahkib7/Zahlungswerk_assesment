package com.bbmk.payment_process.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import com.bbmk.payment_process.service.PaymentTransactionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.procedure.internal.ProcedureCallImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class PaymentControllerTest {
    /**
     * Method under test: {@link PaymentController#createTransaction(PaymentTransaction)}
     */
    @Test
    void testCreateTransaction() {

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

    /**
     * Method under test: {@link PaymentController#createTransaction(PaymentTransaction)}
     */
    @Test
    void testCreateTransaction3() {

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
        paymentTransaction.setCustomer(new Customer());

        // Act
        ResponseEntity<PaymentTransaction> actualCreateTransactionResult = paymentController
            .createTransaction(paymentTransaction);

        // Assert
        assertNull(actualCreateTransactionResult.getBody());
        assertEquals(400, actualCreateTransactionResult.getStatusCodeValue());
        assertTrue(actualCreateTransactionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link PaymentController#createTransaction(PaymentTransaction)}
     */
    @Test
    void testCreateTransaction4() {

        // Arrange
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setMerchant(new Merchant("Name", true));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);

        PaymentTransaction paymentTransaction1 = new PaymentTransaction();
        paymentTransaction1.setCustomer(new Customer());
        paymentTransaction1.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction1.setId(1L);
        paymentTransaction1.setMerchant(new Merchant("Name", true));
        paymentTransaction1.setReceiptId("42");
        paymentTransaction1.setTransactionDate(null);
        paymentTransaction1.setVatRate(VatRate.ZERO_PERCENT);
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findCustomerAndMerchantById((Long) any(), (Long) any()))
            .thenReturn(paymentTransaction);
        when(paymentTransactionRepository.save((PaymentTransaction) any())).thenReturn(paymentTransaction1);
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));

        PaymentTransaction paymentTransaction2 = new PaymentTransaction();
        paymentTransaction2.setCustomer(new Customer());
        paymentTransaction2.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction2.setId(1L);
        paymentTransaction2.setMerchant(new Merchant("Name", true));
        paymentTransaction2.setReceiptId("42");
        paymentTransaction2.setTransactionDate(null);
        paymentTransaction2.setVatRate(VatRate.ZERO_PERCENT);

        // Act
        ResponseEntity<PaymentTransaction> actualCreateTransactionResult = paymentController
            .createTransaction(paymentTransaction2);

        // Assert
        assertNull(actualCreateTransactionResult.getBody());
        assertEquals(400, actualCreateTransactionResult.getStatusCodeValue());
        assertTrue(actualCreateTransactionResult.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findCustomerAndMerchantById((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getAllTransactions()}
     */
    @Test
    void testGetAllTransactions() {

        // Arrange
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findAll()).thenReturn(new ArrayList<>());
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));

        // Act
        ResponseEntity<List<PaymentTransaction>> actualAllTransactions = paymentController.getAllTransactions();

        // Assert
        assertNull(actualAllTransactions.getBody());
        assertEquals(204, actualAllTransactions.getStatusCodeValue());
        assertTrue(actualAllTransactions.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentController#getAllTransactions()}
     */
    @Test
    void testGetAllTransactions2() {

        // Arrange
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setMerchant(new Merchant("Transaction list is empty", true));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);

        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        paymentTransactionList.add(paymentTransaction);
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findAll()).thenReturn(paymentTransactionList);
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));

        // Act
        ResponseEntity<List<PaymentTransaction>> actualAllTransactions = paymentController.getAllTransactions();

        // Assert
        assertTrue(actualAllTransactions.hasBody());
        assertEquals(200, actualAllTransactions.getStatusCodeValue());
        assertTrue(actualAllTransactions.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        PaymentController paymentController = new PaymentController(new PaymentTransactionService(transactionRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))));
        Long merchantId = null;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByMerchant = paymentController
            .getTransactionsByMerchant(merchantId);

        // Assert
        assertNull(actualTransactionsByMerchant.getBody());
        assertEquals(400, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByMerchantId((Long) any())).thenReturn(new ArrayList<>());
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        long merchantId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByMerchant = paymentController
            .getTransactionsByMerchant(merchantId);

        // Assert
        assertNull(actualTransactionsByMerchant.getBody());
        assertEquals(204, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByMerchantId((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setMerchant(new Merchant("Name", true));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);

        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        paymentTransactionList.add(paymentTransaction);
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByMerchantId((Long) any())).thenReturn(paymentTransactionList);
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        long merchantId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByMerchant = paymentController
            .getTransactionsByMerchant(merchantId);

        // Assert
        assertTrue(actualTransactionsByMerchant.hasBody());
        assertEquals(200, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByMerchantId((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTransactionsByMerchant4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.bbmk.payment_process.service.PaymentTransactionService.getTransactionsByMerchant(java.lang.Long)" because "this.transactionService" is null
        //       at com.bbmk.payment_process.controllers.PaymentController.getTransactionsByMerchant(PaymentController.java:77)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        PaymentController paymentController = new PaymentController(null);
        long merchantId = 1L;

        // Act
        paymentController.getTransactionsByMerchant(merchantId);
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant5() throws IllegalArgumentException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionService paymentTransactionService = mock(PaymentTransactionService.class);
        when(paymentTransactionService.getTransactionsByMerchant((Long) any())).thenReturn(new ArrayList<>());
        PaymentController paymentController = new PaymentController(paymentTransactionService);
        long merchantId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByMerchant = paymentController
            .getTransactionsByMerchant(merchantId);

        // Assert
        assertNull(actualTransactionsByMerchant.getBody());
        assertEquals(204, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
        verify(paymentTransactionService).getTransactionsByMerchant((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant6() throws IllegalArgumentException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionService paymentTransactionService = mock(PaymentTransactionService.class);
        when(paymentTransactionService.getTransactionsByMerchant((Long) any())).thenThrow(new IllegalArgumentException());
        PaymentController paymentController = new PaymentController(paymentTransactionService);
        long merchantId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByMerchant = paymentController
            .getTransactionsByMerchant(merchantId);

        // Assert
        assertNull(actualTransactionsByMerchant.getBody());
        assertEquals(400, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
        verify(paymentTransactionService).getTransactionsByMerchant((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByMerchantId((Long) any())).thenThrow(new IllegalArgumentException());
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        long merchantId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByMerchant = paymentController
            .getTransactionsByMerchant(merchantId);

        // Assert
        assertNull(actualTransactionsByMerchant.getBody());
        assertEquals(400, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByMerchantId((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        PaymentController paymentController = new PaymentController(new PaymentTransactionService(transactionRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))));
        Long customerId = null;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByCustomer = paymentController
            .getTransactionsByCustomer(customerId);

        // Assert
        assertNull(actualTransactionsByCustomer.getBody());
        assertEquals(400, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByCustomerId((Long) any())).thenReturn(new ArrayList<>());
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        long customerId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByCustomer = paymentController
            .getTransactionsByCustomer(customerId);

        // Assert
        assertNull(actualTransactionsByCustomer.getBody());
        assertEquals(204, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByCustomerId((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setMerchant(new Merchant("Name", true));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);

        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        paymentTransactionList.add(paymentTransaction);
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByCustomerId((Long) any())).thenReturn(paymentTransactionList);
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        long customerId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByCustomer = paymentController
            .getTransactionsByCustomer(customerId);

        // Assert
        assertTrue(actualTransactionsByCustomer.hasBody());
        assertEquals(200, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByCustomerId((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTransactionsByCustomer4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.bbmk.payment_process.service.PaymentTransactionService.getTransactionsByCustomer(java.lang.Long)" because "this.transactionService" is null
        //       at com.bbmk.payment_process.controllers.PaymentController.getTransactionsByCustomer(PaymentController.java:99)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        PaymentController paymentController = new PaymentController(null);
        long customerId = 1L;

        // Act
        paymentController.getTransactionsByCustomer(customerId);
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer5() throws IllegalArgumentException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionService paymentTransactionService = mock(PaymentTransactionService.class);
        when(paymentTransactionService.getTransactionsByCustomer((Long) any())).thenReturn(new ArrayList<>());
        PaymentController paymentController = new PaymentController(paymentTransactionService);
        long customerId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByCustomer = paymentController
            .getTransactionsByCustomer(customerId);

        // Assert
        assertNull(actualTransactionsByCustomer.getBody());
        assertEquals(204, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
        verify(paymentTransactionService).getTransactionsByCustomer((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer6() throws IllegalArgumentException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionService paymentTransactionService = mock(PaymentTransactionService.class);
        when(paymentTransactionService.getTransactionsByCustomer((Long) any())).thenThrow(new IllegalArgumentException());
        PaymentController paymentController = new PaymentController(paymentTransactionService);
        long customerId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByCustomer = paymentController
            .getTransactionsByCustomer(customerId);

        // Assert
        assertNull(actualTransactionsByCustomer.getBody());
        assertEquals(400, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
        verify(paymentTransactionService).getTransactionsByCustomer((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByCustomerId((Long) any())).thenThrow(new IllegalArgumentException());
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        long customerId = 1L;

        // Act
        ResponseEntity<List<PaymentTransaction>> actualTransactionsByCustomer = paymentController
            .getTransactionsByCustomer(customerId);

        // Assert
        assertNull(actualTransactionsByCustomer.getBody());
        assertEquals(400, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByCustomerId((Long) any());
    }

    /**
     * Method under test: {@link PaymentController#getTotalTurnoverByMerchantAndYear(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTotalTurnoverByMerchantAndYear() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.hibernate.query.spi.QueryProducerImplementor.createQuery(String, java.lang.Class)" because the return value of "org.hibernate.engine.spi.SessionDelegatorBaseImpl.queryDelegate()" is null
        //       at org.hibernate.engine.spi.SessionDelegatorBaseImpl.createQuery(SessionDelegatorBaseImpl.java:522)
        //       at org.hibernate.engine.spi.SessionDelegatorBaseImpl.createQuery(SessionDelegatorBaseImpl.java:522)
        //       at org.hibernate.engine.spi.SessionDelegatorBaseImpl.createQuery(SessionDelegatorBaseImpl.java:522)
        //       at org.hibernate.engine.spi.SessionDelegatorBaseImpl.createQuery(SessionDelegatorBaseImpl.java:79)
        //       at com.bbmk.payment_process.service.PaymentTransactionService.getMerchantWithHighestTurnover(PaymentTransactionService.java:90)
        //       at com.bbmk.payment_process.controllers.PaymentController.getTotalTurnoverByMerchantAndYear(PaymentController.java:115)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        PaymentController paymentController = new PaymentController(new PaymentTransactionService(transactionRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        int year = 1;

        // Act
        paymentController.getTotalTurnoverByMerchantAndYear(year);
    }

    /**
     * Method under test: {@link PaymentController#getTotalTurnoverByMerchantAndYear(Integer)}
     */
    @Test
    void testGetTotalTurnoverByMerchantAndYear2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        PaymentController paymentController = new PaymentController(new PaymentTransactionService(transactionRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))));
        Integer year = null;

        // Act
        ResponseEntity<Merchant> actualTotalTurnoverByMerchantAndYear = paymentController
            .getTotalTurnoverByMerchantAndYear(year);

        // Assert
        assertNull(actualTotalTurnoverByMerchantAndYear.getBody());
        assertEquals(400, actualTotalTurnoverByMerchantAndYear.getStatusCodeValue());
        assertTrue(actualTotalTurnoverByMerchantAndYear.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link PaymentController#getTotalTurnoverByMerchantAndYear(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTotalTurnoverByMerchantAndYear3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Iterable.iterator()" because "iterable" is null
        //       at org.hibernate.procedure.internal.ProcedureParameterMetadataImpl.findQueryParameter(ProcedureParameterMetadataImpl.java:141)
        //       at org.hibernate.procedure.internal.ProcedureParameterMetadataImpl.getQueryParameter(ProcedureParameterMetadataImpl.java:151)
        //       at org.hibernate.procedure.internal.ProcedureParameterMetadataImpl.getQueryParameter(ProcedureParameterMetadataImpl.java:34)
        //       at org.hibernate.query.spi.AbstractCommonQueryContract.setParameter(AbstractCommonQueryContract.java:844)
        //       at org.hibernate.query.spi.AbstractSelectionQuery.setParameter(AbstractSelectionQuery.java:708)
        //       at org.hibernate.query.spi.AbstractQuery.setParameter(AbstractQuery.java:369)
        //       at org.hibernate.procedure.internal.ProcedureCallImpl.setParameter(ProcedureCallImpl.java:1135)
        //       at org.hibernate.procedure.internal.ProcedureCallImpl.setParameter(ProcedureCallImpl.java:103)
        //       at com.bbmk.payment_process.service.PaymentTransactionService.getMerchantWithHighestTurnover(PaymentTransactionService.java:92)
        //       at com.bbmk.payment_process.controllers.PaymentController.getTotalTurnoverByMerchantAndYear(PaymentController.java:115)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen(anyBoolean());
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.createQuery((String) any(), (Class<Merchant>) any()))
            .thenReturn(new ProcedureCallImpl<>(
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
                    new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl)))),
                "Procedure Name"));
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(mock(PaymentTransactionRepository.class),
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1))));
        int year = 1;

        // Act
        paymentController.getTotalTurnoverByMerchantAndYear(year);
    }

    /**
     * Method under test: {@link PaymentController#getTotalTurnoverByMerchantAndYear(Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTotalTurnoverByMerchantAndYear4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "jakarta.persistence.TypedQuery.setParameter(String, Object)" because "query" is null
        //       at com.bbmk.payment_process.service.PaymentTransactionService.getMerchantWithHighestTurnover(PaymentTransactionService.java:92)
        //       at com.bbmk.payment_process.controllers.PaymentController.getTotalTurnoverByMerchantAndYear(PaymentController.java:115)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen(anyBoolean());
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl))));
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.createQuery((String) any(), (Class<Merchant>) any())).thenReturn(null);
        PaymentController paymentController = new PaymentController(
            new PaymentTransactionService(mock(PaymentTransactionRepository.class),
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1))));
        int year = 1;

        // Act
        paymentController.getTotalTurnoverByMerchantAndYear(year);
    }
}

