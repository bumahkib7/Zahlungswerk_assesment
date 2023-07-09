package com.bbmk.payment_process.service;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.repositories.MerchantRepository;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import com.bbmk.payment_process.requests.TransferMoneyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PaymentTransactionService.class})
@ExtendWith(SpringExtension.class)
class PaymentTransactionServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private MerchantRepository merchantRepository;

    @MockBean
    private MoneyTransferService moneyTransferService;

    @MockBean
    private PaymentTransactionRepository paymentTransactionRepository;

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    /**
     * Method under test: {@link PaymentTransactionService#createTransaction(Long, Long, BigDecimal, VatRate)}
     */
    @Test
    void testCreateTransaction() {
        // Arrange
        Long customerId = 1L;
        Long merchantId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        VatRate vatRate = VatRate.ZERO_PERCENT;

        Customer customer = new Customer();
        customer.setId(customerId);

        Merchant merchant = new Merchant();
        merchant.setId(merchantId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(merchant));
        when(paymentTransactionRepository.save(any(PaymentTransaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentTransactionRepository.existsById(any(UUID.class))).thenReturn(false);
        doNothing().when(moneyTransferService).transferMoney(any(TransferMoneyRequest.class));

        PaymentTransaction actualCreateTransactionResult = paymentTransactionService.createTransaction(customerId, merchantId, amount, vatRate);

        assertEquals(customerId, actualCreateTransactionResult.getCustomer().getId());
        assertEquals(merchantId, actualCreateTransactionResult.getMerchant().getId());
        assertEquals(amount, actualCreateTransactionResult.getGrossAmount());
        assertEquals(vatRate, actualCreateTransactionResult.getVatRate());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    void testGetAllTransactions() {
        when(paymentTransactionRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(EmptyTransactionListException.class, () -> paymentTransactionService.getAllTransactions());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentTransactionService#getAllTransactions()}
     */
    @Test
    void testGetAllTransactions2() throws EmptyTransactionListException {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(UUID.randomUUID());
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
     * Method under test: {@link PaymentTransactionService#getTransactionsByMerchant(Long)}
     */
    @Test
    void testGetTransactionsByMerchant() throws IllegalArgumentException {
        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        when(paymentTransactionRepository.findByMerchantId(any())).thenReturn(paymentTransactionList);
        List<PaymentTransaction> actualTransactionsByMerchant = paymentTransactionService.getTransactionsByMerchant(1L);
        assertSame(paymentTransactionList, actualTransactionsByMerchant);
        assertTrue(actualTransactionsByMerchant.isEmpty());
        verify(paymentTransactionRepository).findByMerchantId(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionsByCustomer(Long)}
     */
    @Test
    void testGetTransactionsByCustomer() throws IllegalArgumentException {
        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        when(paymentTransactionRepository.findByCustomerId(any())).thenReturn(paymentTransactionList);
        List<PaymentTransaction> actualTransactionsByCustomer = paymentTransactionService.getTransactionsByCustomer(1L);
        assertSame(paymentTransactionList, actualTransactionsByCustomer);
        assertTrue(actualTransactionsByCustomer.isEmpty());
        verify(paymentTransactionRepository).findByCustomerId(any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#deleteTransaction(UUID)}
     */
    @Test
    void testDeleteTransaction() {
        doNothing().when(paymentTransactionRepository).deleteById((UUID) any());
        paymentTransactionService.deleteTransaction(UUID.randomUUID());
        verify(paymentTransactionRepository).deleteById((UUID) any());
    }

    /**
     * Method under test: {@link PaymentTransactionService#getTransactionById(long)}
     */
    @Test
    void testGetTransactionById() {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(UUID.randomUUID());
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
}

