package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.exceptions.EmptyTransactionListException;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.models.constants.VatRate;
import com.bbmk.payment_process.repositories.PaymentTransactionRepository;
import com.bbmk.payment_process.service.PaymentTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.NonUniqueResultException;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PaymentController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentControllerTest {
    @Autowired
    private PaymentController paymentController;

    @MockBean
    private PaymentTransactionRepository paymentTransactionRepository;

    @MockBean
    private PaymentTransactionService paymentTransactionService;

    /**
     * Method under test: {@link PaymentController#createTransaction(PaymentTransaction)}
     */
    @Test
    @Ignore
    public void testCreateTransaction() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Alice");
        customer.setEmail("alice@example.com");

        Merchant merchant = new Merchant("Bob's Shop", true);

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(customer);
        paymentTransaction.setMerchant(merchant);
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(paymentTransaction);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1/payment/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        Object[] controllers = new Object[]{paymentController};
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllers).build();

        // Act
        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

        PaymentTransaction createdTransaction = objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            PaymentTransaction.class);

        assertNotNull(createdTransaction.getId());
        assertEquals(paymentTransaction.getCustomer().getId(), createdTransaction.getCustomer().getId());
        assertEquals(paymentTransaction.getMerchant(), createdTransaction.getMerchant());
        assertEquals(paymentTransaction.getGrossAmount(), createdTransaction.getGrossAmount());
        assertEquals(paymentTransaction.getReceiptId(), createdTransaction.getReceiptId());
        assertEquals(paymentTransaction.getVatRate(), createdTransaction.getVatRate());
    }

    /**
     * Method under test: {@link PaymentController#createTransaction(PaymentTransaction)}
     */
    @Test
    public void testCreateTransaction2() throws IllegalArgumentException {

        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setCustomer(new Customer());
        paymentTransaction.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction.setId(1L);
        paymentTransaction.setMerchant(new Merchant("Name", true));
        paymentTransaction.setReceiptId("42");
        paymentTransaction.setTransactionDate(null);
        paymentTransaction.setVatRate(VatRate.ZERO_PERCENT);
        PaymentTransactionService paymentTransactionService = mock(PaymentTransactionService.class);
        when(paymentTransactionService.createTransaction(any())).thenReturn(paymentTransaction);
        PaymentController paymentController = new PaymentController(paymentTransactionService,
            mock(PaymentTransactionRepository.class));

        PaymentTransaction paymentTransaction1 = new PaymentTransaction();
        paymentTransaction1.setCustomer(new Customer());
        paymentTransaction1.setGrossAmount(BigDecimal.valueOf(42L));
        paymentTransaction1.setId(1L);
        paymentTransaction1.setMerchant(new Merchant("Name", true));
        paymentTransaction1.setReceiptId("42");
        paymentTransaction1.setTransactionDate(null);
        paymentTransaction1.setVatRate(VatRate.ZERO_PERCENT);
        ResponseEntity<PaymentTransaction> actualCreateTransactionResult = paymentController
            .createTransaction(paymentTransaction1);
        assertTrue(actualCreateTransactionResult.hasBody());
        assertTrue(actualCreateTransactionResult.getHeaders().isEmpty());
        org.junit.Assert.assertEquals(201, actualCreateTransactionResult.getStatusCodeValue());
        org.junit.Assert.assertEquals("42", actualCreateTransactionResult.getBody().getGrossAmount().toString());
        verify(paymentTransactionService).createTransaction(any());
    }

    /**
     * Method under test: {@link PaymentController#getAllTransactions()}
     */
    @Test
    public void testGetAllTransactions() throws EmptyTransactionListException {

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
        ResponseEntity<List<PaymentTransaction>> actualAllTransactions = (new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            mock(PaymentTransactionRepository.class))).getAllTransactions();
        assertTrue(actualAllTransactions.hasBody());
        org.junit.Assert.assertEquals(200, actualAllTransactions.getStatusCodeValue());
        assertTrue(actualAllTransactions.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentController#getAllTransactions()}
     */
    @Test
    public void testGetAllTransactions2() throws EmptyTransactionListException {

        PaymentTransactionService paymentTransactionService = mock(PaymentTransactionService.class);
        when(paymentTransactionService.getAllTransactions()).thenReturn(new ArrayList<>());
        ResponseEntity<List<PaymentTransaction>> actualAllTransactions = (new PaymentController(paymentTransactionService,
            mock(PaymentTransactionRepository.class))).getAllTransactions();
        assertNull(actualAllTransactions.getBody());
        org.junit.Assert.assertEquals(204, actualAllTransactions.getStatusCodeValue());
        assertTrue(actualAllTransactions.getHeaders().isEmpty());
        verify(paymentTransactionService).getAllTransactions();
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    public void testGetTransactionsByMerchant() {

        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByMerchantId(any())).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualTransactionsByMerchant = (new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            mock(PaymentTransactionRepository.class))).getTransactionsByMerchant(1L);
        assertNull(actualTransactionsByMerchant.getBody());
        org.junit.Assert.assertEquals(204, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByMerchantId(any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByMerchant(Long)}
     */
    @Test
    public void testGetTransactionsByMerchant2() {

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
        when(paymentTransactionRepository.findByMerchantId(any())).thenReturn(paymentTransactionList);
        ResponseEntity<?> actualTransactionsByMerchant = (new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            mock(PaymentTransactionRepository.class))).getTransactionsByMerchant(1L);
        assertTrue(actualTransactionsByMerchant.hasBody());
        org.junit.Assert.assertEquals(200, actualTransactionsByMerchant.getStatusCodeValue());
        assertTrue(actualTransactionsByMerchant.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByMerchantId(any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    public void testGetTransactionsByCustomer() {

        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findByCustomerId(any())).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualTransactionsByCustomer = (new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            mock(PaymentTransactionRepository.class))).getTransactionsByCustomer(1L);
        assertNull(actualTransactionsByCustomer.getBody());
        org.junit.Assert.assertEquals(204, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByCustomerId(any());
    }

    /**
     * Method under test: {@link PaymentController#getTransactionsByCustomer(Long)}
     */
    @Test
    public void testGetTransactionsByCustomer2() {

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
        when(paymentTransactionRepository.findByCustomerId(any())).thenReturn(paymentTransactionList);
        ResponseEntity<?> actualTransactionsByCustomer = (new PaymentController(
            new PaymentTransactionService(paymentTransactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            mock(PaymentTransactionRepository.class))).getTransactionsByCustomer(1L);
        assertTrue(actualTransactionsByCustomer.hasBody());
        org.junit.Assert.assertEquals(200, actualTransactionsByCustomer.getStatusCodeValue());
        assertTrue(actualTransactionsByCustomer.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findByCustomerId(any());
    }

    /**
     * Method under test: {@link PaymentController#getTotalTurnoverByMerchantAndYear(Integer)}
     */
    @Test
    public void testGetTotalTurnoverByMerchantAndYear() throws NonUniqueResultException {

        PaymentTransactionService paymentTransactionService = mock(PaymentTransactionService.class);
        when(paymentTransactionService.getMerchantWithHighestTurnover(anyInt())).thenReturn(new Merchant("Name", true));
        ResponseEntity<Merchant> actualTotalTurnoverByMerchantAndYear = (new PaymentController(paymentTransactionService,
            mock(PaymentTransactionRepository.class))).getTotalTurnoverByMerchantAndYear(1);
        assertTrue(actualTotalTurnoverByMerchantAndYear.hasBody());
        assertTrue(actualTotalTurnoverByMerchantAndYear.getHeaders().isEmpty());
        org.junit.Assert.assertEquals(200, actualTotalTurnoverByMerchantAndYear.getStatusCodeValue());
        verify(paymentTransactionService).getMerchantWithHighestTurnover(anyInt());
    }

    /**
     * Method under test: {@link PaymentController#findPaymentByMerchantID(Long)}
     */
    @Test
    public void testFindPaymentByMerchantID() {

        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.findPaymentTransactionByMerchantId(any())).thenReturn(new ArrayList<>());
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        ResponseEntity<?> actualFindPaymentByMerchantIDResult = (new PaymentController(
            new PaymentTransactionService(transactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            paymentTransactionRepository)).findPaymentByMerchantID(1L);
        assertNull(actualFindPaymentByMerchantIDResult.getBody());
        org.junit.Assert.assertEquals(204, actualFindPaymentByMerchantIDResult.getStatusCodeValue());
        assertTrue(actualFindPaymentByMerchantIDResult.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findPaymentTransactionByMerchantId(any());
    }

    /**
     * Method under test: {@link PaymentController#findPaymentByMerchantID(Long)}
     */
    @Test
    public void testFindPaymentByMerchantID2() {

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
        when(paymentTransactionRepository.findPaymentTransactionByMerchantId(any()))
            .thenReturn(paymentTransactionList);
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        ResponseEntity<?> actualFindPaymentByMerchantIDResult = (new PaymentController(
            new PaymentTransactionService(transactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            paymentTransactionRepository)).findPaymentByMerchantID(1L);
        assertTrue(actualFindPaymentByMerchantIDResult.hasBody());
        org.junit.Assert.assertEquals(200, actualFindPaymentByMerchantIDResult.getStatusCodeValue());
        assertTrue(actualFindPaymentByMerchantIDResult.getHeaders().isEmpty());
        verify(paymentTransactionRepository).findPaymentTransactionByMerchantId(any());
    }

    /**
     * Method under test: {@link PaymentController#getTotalTurnoverYear(Integer)}
     */
    @Test
    public void testGetTotalTurnoverYear() throws NonUniqueResultException {

        PaymentTransactionRepository paymentTransactionRepository = mock(PaymentTransactionRepository.class);
        when(paymentTransactionRepository.getMerchantWithHighestTurnoverinYear(anyInt()))
            .thenReturn(new Merchant("Name", true));
        PaymentTransactionRepository transactionRepository = mock(PaymentTransactionRepository.class);
        ResponseEntity<Merchant> actualTotalTurnoverYear = (new PaymentController(
            new PaymentTransactionService(transactionRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))),
            paymentTransactionRepository)).getTotalTurnoverYear(1);
        assertTrue(actualTotalTurnoverYear.hasBody());
        assertTrue(actualTotalTurnoverYear.getHeaders().isEmpty());
        org.junit.Assert.assertEquals(200, actualTotalTurnoverYear.getStatusCodeValue());
        verify(paymentTransactionRepository).getMerchantWithHighestTurnoverinYear(anyInt());
    }

}

