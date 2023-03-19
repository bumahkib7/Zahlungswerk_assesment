package com.bbmk.payment_process.service;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PaymentTransactionService.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentTransactionServiceTest {
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
    public void testCreateTransaction() throws IllegalArgumentException {
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
        when(paymentTransactionRepository.findCustomerAndMerchantById(any(), any()))
            .thenReturn(paymentTransaction);
        when(paymentTransactionRepository.save(any())).thenReturn(paymentTransaction1);

        PaymentTransaction paymentTransaction2 = new PaymentTransaction();
        paymentTransaction2.setCustomer(new Customer());
        paymentTransaction2.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction2.setId(1L);
        paymentTransaction2.setMerchant(new Merchant("Name", true));
        paymentTransaction2.setReceiptId("42");
        paymentTransaction2.setTransactionDate(null);
        paymentTransaction2.setVatRate(VatRate.ZERO_PERCENT);
        assertThrows(IllegalArgumentException.class,
            () -> paymentTransactionService.createTransaction(paymentTransaction2));
        verify(paymentTransactionRepository).findCustomerAndMerchantById(any(), any());
    }


    /**
     * Method under test: {@link PaymentTransactionService#createTransaction(PaymentTransaction)}
     */
    @Test
    public void testCreateTransaction3() throws IllegalArgumentException {
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
        when(paymentTransactionRepository.findCustomerAndMerchantById(any(), any()))
            .thenReturn(paymentTransaction);
        when(paymentTransactionRepository.save(any())).thenReturn(paymentTransaction1);
        PaymentTransaction paymentTransaction2 = mock(PaymentTransaction.class);
        when(paymentTransaction2.getMerchant()).thenReturn(null);
        when(paymentTransaction2.getCustomer()).thenReturn(new Customer());
        doNothing().when(paymentTransaction2).setCustomer(any());
        doNothing().when(paymentTransaction2).setGrossAmount(any());
        doNothing().when(paymentTransaction2).setId(any());
        doNothing().when(paymentTransaction2).setMerchant(any());
        doNothing().when(paymentTransaction2).setReceiptId(any());
        doNothing().when(paymentTransaction2).setTransactionDate(any());
        doNothing().when(paymentTransaction2).setVatRate(any());
        paymentTransaction2.setCustomer(new Customer());
        paymentTransaction2.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction2.setId(1L);
        paymentTransaction2.setMerchant(new Merchant("Name", true));
        paymentTransaction2.setReceiptId("42");
        paymentTransaction2.setTransactionDate(null);
        paymentTransaction2.setVatRate(VatRate.ZERO_PERCENT);
        assertThrows(IllegalArgumentException.class,
            () -> paymentTransactionService.createTransaction(paymentTransaction2));
        verify(paymentTransaction2).getCustomer();
        verify(paymentTransaction2).getMerchant();
        verify(paymentTransaction2).setCustomer(any());
        verify(paymentTransaction2).setGrossAmount(any());
        verify(paymentTransaction2).setId(any());
        verify(paymentTransaction2).setMerchant(any());
        verify(paymentTransaction2).setReceiptId(any());
        verify(paymentTransaction2).setTransactionDate(any());
        verify(paymentTransaction2).setVatRate(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#createTransaction(PaymentTransaction)}
     */
    @Test
    public void testCreateTransaction4() throws IllegalArgumentException {
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
        when(paymentTransactionRepository.findCustomerAndMerchantById(any(), any()))
            .thenReturn(paymentTransaction);
        when(paymentTransactionRepository.save(any())).thenReturn(paymentTransaction1);
        PaymentTransaction paymentTransaction2 = mock(PaymentTransaction.class);
        when(paymentTransaction2.getMerchant()).thenReturn(new Merchant("Name", true));
        when(paymentTransaction2.getCustomer()).thenReturn(null);
        doNothing().when(paymentTransaction2).setCustomer(any());
        doNothing().when(paymentTransaction2).setGrossAmount(any());
        doNothing().when(paymentTransaction2).setId(any());
        doNothing().when(paymentTransaction2).setMerchant(any());
        doNothing().when(paymentTransaction2).setReceiptId(any());
        doNothing().when(paymentTransaction2).setTransactionDate(any());
        doNothing().when(paymentTransaction2).setVatRate(any());
        paymentTransaction2.setCustomer(new Customer());
        paymentTransaction2.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction2.setId(1L);
        paymentTransaction2.setMerchant(new Merchant("Name", true));
        paymentTransaction2.setReceiptId("42");
        paymentTransaction2.setTransactionDate(null);
        paymentTransaction2.setVatRate(VatRate.ZERO_PERCENT);
        assertThrows(IllegalArgumentException.class,
            () -> paymentTransactionService.createTransaction(paymentTransaction2));
        verify(paymentTransaction2).getCustomer();
        verify(paymentTransaction2).setCustomer(any());
        verify(paymentTransaction2).setGrossAmount(any());
        verify(paymentTransaction2).setId(any());
        verify(paymentTransaction2).setMerchant(any());
        verify(paymentTransaction2).setReceiptId(any());
        verify(paymentTransaction2).setTransactionDate(any());
        verify(paymentTransaction2).setVatRate(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    public void testGetAllTransactions() throws EmptyTransactionListException {
        when(paymentTransactionRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EmptyTransactionListException.class, () -> paymentTransactionService.getAllTransactions());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    public void testGetAllTransactions2() throws EmptyTransactionListException {
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
        List<PaymentTransaction> actualAllTransactions = paymentTransactionService.getAllTransactions();
        assertSame(paymentTransactionList, actualAllTransactions);
        assertEquals(1, actualAllTransactions.size());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    public void testGetAllTransactions3() throws EmptyTransactionListException {
        when(paymentTransactionRepository.findAll()).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.getAllTransactions());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByMerchant(Long)}
     */
    @Test
    public void testGetTransactionsByMerchant() throws IllegalArgumentException {
        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        when(paymentTransactionRepository.findByMerchantId(any())).thenReturn(paymentTransactionList);
        List<PaymentTransaction> actualTransactionsByMerchant = paymentTransactionService.getTransactionsByMerchant(1L);
        assertSame(paymentTransactionList, actualTransactionsByMerchant);
        assertTrue(actualTransactionsByMerchant.isEmpty());
        verify(paymentTransactionRepository).findByMerchantId(any());
    }


    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByMerchant(Long)}
     */
    @Test
    public void testGetTransactionsByMerchant3() throws IllegalArgumentException {
        when(paymentTransactionRepository.findByMerchantId(any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.getTransactionsByMerchant(1L));
        verify(paymentTransactionRepository).findByMerchantId(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByCustomer(Long)}
     */
    @Test
    public void testGetTransactionsByCustomer() throws IllegalArgumentException {
        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        when(paymentTransactionRepository.findByCustomerId(any())).thenReturn(paymentTransactionList);
        List<PaymentTransaction> actualTransactionsByCustomer = paymentTransactionService.getTransactionsByCustomer(1L);
        assertSame(paymentTransactionList, actualTransactionsByCustomer);
        assertTrue(actualTransactionsByCustomer.isEmpty());
        verify(paymentTransactionRepository).findByCustomerId(any());
    }


    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByCustomer(Long)}
     */
    @Test
    public void testGetTransactionsByCustomer3() throws IllegalArgumentException {
        when(paymentTransactionRepository.findByCustomerId(any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.getTransactionsByCustomer(1L));
        verify(paymentTransactionRepository).findByCustomerId(any());
    }


    /**
     * Method under test: {@link PaymentTransactionService#deleteTransaction(long)}
     */
    @Test
    public void testDeleteTransaction() {
        doNothing().when(paymentTransactionRepository).deleteById(any());
        paymentTransactionService.deleteTransaction(1L);
        verify(paymentTransactionRepository).deleteById(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#deleteTransaction(long)}
     */
    @Test
    public void testDeleteTransaction2() {
        doThrow(new IllegalArgumentException()).when(paymentTransactionRepository).deleteById(any());
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.deleteTransaction(1L));
        verify(paymentTransactionRepository).deleteById(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionById(long)}
     */
    @Test
    public void testGetTransactionById() {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setMerchant(new Merchant("Name", true));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);
        Optional<PaymentTransaction> ofResult = Optional.of(paymentTransaction);
        when(paymentTransactionRepository.findById(any())).thenReturn(ofResult);
        Optional<PaymentTransaction> actualTransactionById = paymentTransactionService.getTransactionById(1L);
        assertSame(ofResult, actualTransactionById);
        assertTrue(actualTransactionById.isPresent());
        assertEquals("42", actualTransactionById.get().getGrossAmount().toString());
        verify(paymentTransactionRepository).findById(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionById(long)}
     */
    @Test
    public void testGetTransactionById2() {
        when(paymentTransactionRepository.findById(any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> paymentTransactionService.getTransactionById(1L));
        verify(paymentTransactionRepository).findById(any());
    }
}

