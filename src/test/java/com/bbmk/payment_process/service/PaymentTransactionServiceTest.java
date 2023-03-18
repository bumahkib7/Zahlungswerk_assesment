package com.bbmk.payment_process.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

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
    @Disabled("TODO: Complete this test")
    void testCreateTransaction2() throws IllegalArgumentException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException
        //       at com.bbmk.payment_process.models.PaymentTransaction.getMerchant(PaymentTransaction.java:42)
        //       at com.bbmk.payment_process.service.PaymentTransactionService.createTransaction(PaymentTransactionService.java:33)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(paymentTransaction2.getMerchant()).thenThrow(new IllegalArgumentException());
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

        // Act
        paymentTransactionService.createTransaction(paymentTransaction2);
    }
}

