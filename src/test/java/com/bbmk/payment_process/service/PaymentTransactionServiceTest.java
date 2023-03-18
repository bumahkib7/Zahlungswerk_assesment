package com.bbmk.payment_process.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.TypedQuery;
import org.hibernate.NonUniqueResultException;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.procedure.internal.ProcedureCallImpl;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PaymentTransactionService.class})
@ExtendWith(SpringExtension.class)
class PaymentTransactionServiceTest {
    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    /**
     * Method under test: {@link PaymentTransactionService#createTransaction(PaymentTransaction)}
     */
    @Test
    void testCreateTransaction() throws IllegalArgumentException {
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
        when(paymentTransactionRepository.findCustomerAndMerchantById((Long) any(), (Long) any()))
            .thenReturn(paymentTransaction);
        when(paymentTransactionRepository.save((PaymentTransaction) any())).thenReturn(paymentTransaction1);

        PaymentTransaction paymentTransaction2 = new PaymentTransaction();
        paymentTransaction2.setCustomer(new Customer());
        paymentTransaction2.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction2.setId(1L);
        paymentTransaction2.setMerchant(new Merchant("Name", true));
        paymentTransaction2.setReceiptId("42");
        paymentTransaction2.setTransactionDate(null);
        paymentTransaction2.setVatRate(VatRate.ZERO_PERCENT);

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
            () -> paymentTransactionService.createTransaction(paymentTransaction2));
        verify(paymentTransactionRepository).findCustomerAndMerchantById((Long) any(), (Long) any());
    }


    /**
     * Method under test: {@link PaymentTransactionService#createTransaction(PaymentTransaction)}
     */
    @Test
    void testCreateTransaction3() throws IllegalArgumentException {
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
        when(paymentTransactionRepository.findCustomerAndMerchantById((Long) any(), (Long) any()))
            .thenReturn(paymentTransaction);
        when(paymentTransactionRepository.save((PaymentTransaction) any())).thenReturn(paymentTransaction1);
        PaymentTransaction paymentTransaction2 = mock(PaymentTransaction.class);
        when(paymentTransaction2.getMerchant()).thenReturn(null);
        when(paymentTransaction2.getCustomer()).thenReturn(new Customer());
        doNothing().when(paymentTransaction2).setCustomer((Customer) any());
        doNothing().when(paymentTransaction2).setGrossAmount((BigDecimal) any());
        doNothing().when(paymentTransaction2).setId((Long) any());
        doNothing().when(paymentTransaction2).setMerchant((Merchant) any());
        doNothing().when(paymentTransaction2).setReceiptId((String) any());
        doNothing().when(paymentTransaction2).setTransactionDate((ZonedDateTime) any());
        doNothing().when(paymentTransaction2).setVatRate((VatRate) any());
        paymentTransaction2.setCustomer(new Customer());
        paymentTransaction2.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction2.setId(1L);
        paymentTransaction2.setMerchant(new Merchant("Name", true));
        paymentTransaction2.setReceiptId("42");
        paymentTransaction2.setTransactionDate(null);
        paymentTransaction2.setVatRate(VatRate.ZERO_PERCENT);

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
            () -> paymentTransactionService.createTransaction(paymentTransaction2));
        verify(paymentTransaction2).getCustomer();
        verify(paymentTransaction2).getMerchant();
        verify(paymentTransaction2).setCustomer((Customer) any());
        verify(paymentTransaction2).setGrossAmount((BigDecimal) any());
        verify(paymentTransaction2).setId((Long) any());
        verify(paymentTransaction2).setMerchant((Merchant) any());
        verify(paymentTransaction2).setReceiptId((String) any());
        verify(paymentTransaction2).setTransactionDate((ZonedDateTime) any());
        verify(paymentTransaction2).setVatRate((VatRate) any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    void testGetAllTransactions() {
        // Arrange
        when(paymentTransactionRepository.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(EmptyTransactionListException.class, () -> paymentTransactionService.getAllTransactions());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    void testGetAllTransactions2() throws EmptyTransactionListException {
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
        when(paymentTransactionRepository.findAll()).thenReturn(paymentTransactionList);

        // Act
        List<PaymentTransaction> actualAllTransactions = paymentTransactionService.getAllTransactions();

        // Assert
        assertSame(paymentTransactionList, actualAllTransactions);
        assertEquals(1, actualAllTransactions.size());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    void testGetAllTransactions3() throws EmptyTransactionListException {
        // Arrange
        when(paymentTransactionRepository.findAll()).thenThrow(new IllegalArgumentException());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.getAllTransactions());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant() throws IllegalArgumentException {
        // Arrange
        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        when(paymentTransactionRepository.findByMerchantId((Long) any())).thenReturn(paymentTransactionList);
        long merchantId = 1L;

        // Act
        List<PaymentTransaction> actualTransactionsByMerchant = paymentTransactionService
            .getTransactionsByMerchant(merchantId);

        // Assert
        assertSame(paymentTransactionList, actualTransactionsByMerchant);
        assertTrue(actualTransactionsByMerchant.isEmpty());
        verify(paymentTransactionRepository).findByMerchantId((Long) any());
    }



    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant3() throws IllegalArgumentException {
        // Arrange
        when(paymentTransactionRepository.findByMerchantId((Long) any())).thenThrow(new IllegalArgumentException());
        long merchantId = 1L;

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
            () -> paymentTransactionService.getTransactionsByMerchant(merchantId));
        verify(paymentTransactionRepository).findByMerchantId((Long) any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer() throws IllegalArgumentException {
        // Arrange
        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        when(paymentTransactionRepository.findByCustomerId((Long) any())).thenReturn(paymentTransactionList);
        long customerId = 1L;

        // Act
        List<PaymentTransaction> actualTransactionsByCustomer = paymentTransactionService
            .getTransactionsByCustomer(customerId);

        // Assert
        assertSame(paymentTransactionList, actualTransactionsByCustomer);
        assertTrue(actualTransactionsByCustomer.isEmpty());
        verify(paymentTransactionRepository).findByCustomerId((Long) any());
    }



    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer3() throws IllegalArgumentException {
        // Arrange
        when(paymentTransactionRepository.findByCustomerId((Long) any())).thenThrow(new IllegalArgumentException());
        long customerId = 1L;

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
            () -> paymentTransactionService.getTransactionsByCustomer(customerId));
        verify(paymentTransactionRepository).findByCustomerId((Long) any());
    }


    /**
     * Method under test: {@link PaymentTransactionService#deleteTransaction(long)}
     */
    @Test
    void testDeleteTransaction() {
        // Arrange
        doNothing().when(paymentTransactionRepository).deleteById((Long) any());
        long id = 1L;

        // Act
        paymentTransactionService.deleteTransaction(id);

        // Assert that nothing has changed
        verify(paymentTransactionRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#deleteTransaction(long)}
     */
    @Test
    void testDeleteTransaction2() {
        // Arrange
        doThrow(new IllegalArgumentException()).when(paymentTransactionRepository).deleteById((Long) any());
        long id = 1L;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.deleteTransaction(id));
        verify(paymentTransactionRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionById(long)}
     */
    @Test
    void testGetTransactionById() {
        // Arrange
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setMerchant(new Merchant("Name", true));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);
        Optional<PaymentTransaction> ofResult = Optional.of(paymentTransaction);
        when(paymentTransactionRepository.findById((Long) any())).thenReturn(ofResult);
        long l = 1L;

        // Act
        Optional<PaymentTransaction> actualTransactionById = paymentTransactionService.getTransactionById(l);

        // Assert
        assertSame(ofResult, actualTransactionById);
        assertTrue(actualTransactionById.isPresent());
        assertEquals("42", actualTransactionById.get().getGrossAmount().toString());
        verify(paymentTransactionRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionById(long)}
     */
    @Test
    void testGetTransactionById2() {
        // Arrange
        when(paymentTransactionRepository.findById((Long) any())).thenThrow(new IllegalArgumentException());
        long l = 1L;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.getTransactionById(l));
        verify(paymentTransactionRepository).findById((Long) any());
    }


}

