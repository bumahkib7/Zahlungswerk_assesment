package com.bbmk.payment_process.service;

import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.repositories.MerchantRepository;
import com.bbmk.payment_process.requests.TransferMoneyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MoneyTransferService.class})
@ExtendWith(SpringExtension.class)
class MoneyTransferServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private MerchantRepository merchantRepository;

    @Autowired
    private MoneyTransferService moneyTransferService;

    @MockBean
    private TransactionTemplate transactionTemplate;

    /**
     * Method under test: {@link MoneyTransferService#transferMoney(TransferMoneyRequest)}
     */
    @Test
    void testTransferMoney() throws TransactionException {
        when(transactionTemplate.execute(any())).thenReturn("Execute");

        TransferMoneyRequest transferMoneyRequest = new TransferMoneyRequest();
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transferMoneyRequest.setAmount(valueOfResult);
        transferMoneyRequest.setCustomerId(1L);
        transferMoneyRequest.setMerchantId(1L);
        moneyTransferService.transferMoney(transferMoneyRequest);
        verify(transactionTemplate).execute(any());
        assertSame(valueOfResult, transferMoneyRequest.getAmount());
        assertEquals(1L, transferMoneyRequest.getMerchantId().longValue());
        assertEquals(1L, transferMoneyRequest.getCustomerId().longValue());
    }
}

