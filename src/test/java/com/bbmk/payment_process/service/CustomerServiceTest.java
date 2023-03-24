package com.bbmk.payment_process.service;

import com.bbmk.payment_process.Dto.CustomerDTO;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Method under test: {@link CustomerService#findCustomerById(Long)}
     */
    @Test
    void testFindCustomerById() {


        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer(1L,
            "@NotNull method %s.%s must not return null", "jane.doe@example.org", LocalDate.ofEpochDay(1L), true)));
        CustomerDTO actualFindCustomerByIdResult = (new CustomerService(customerRepository,
            mock(NamedParameterJdbcTemplate.class))).findCustomerById(1L);
        assertEquals("1970-01-02", actualFindCustomerByIdResult.getDateOfRegistration().toString());
        assertTrue(actualFindCustomerByIdResult.isActive());
        assertTrue(actualFindCustomerByIdResult.getTransactions().isEmpty());
        assertEquals("@NotNull method %s.%s must not return null", actualFindCustomerByIdResult.getName());
        assertEquals(1L, actualFindCustomerByIdResult.getId().longValue());
        assertEquals("jane.doe@example.org", actualFindCustomerByIdResult.getEmail());
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#findCustomerById(Long)}
     */
    @Test
    void testFindCustomerById2() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        LocalDate dateOfRegistration = LocalDate.ofEpochDay(1L);
        BigDecimal balance = BigDecimal.valueOf(42L);
        ArrayList<PaymentTransaction> paymentTransactionList = new ArrayList<>();
        when(customerRepository.findById(any()))
            .thenReturn(Optional.of(new Customer(1L, 1L, "@NotNull method %s.%s must not return null",
                "jane.doe@example.org", dateOfRegistration, true, balance, paymentTransactionList)));
        CustomerDTO actualFindCustomerByIdResult = (new CustomerService(customerRepository,
            mock(NamedParameterJdbcTemplate.class))).findCustomerById(1L);
        assertEquals("1970-01-02", actualFindCustomerByIdResult.getDateOfRegistration().toString());
        assertTrue(actualFindCustomerByIdResult.isActive());
        assertEquals(paymentTransactionList, actualFindCustomerByIdResult.getTransactions());
        assertEquals("@NotNull method %s.%s must not return null", actualFindCustomerByIdResult.getName());
        assertEquals(1L, actualFindCustomerByIdResult.getId().longValue());
        assertEquals("jane.doe@example.org", actualFindCustomerByIdResult.getEmail());
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#findCustomerById(Long)}
     */
    @Test
    void testFindCustomerById3() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
            () -> (new CustomerService(customerRepository, mock(NamedParameterJdbcTemplate.class))).findCustomerById(1L));
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getAllCustomers(Pageable)}
     */
    @Test
    void testGetAllCustomers() {
        PageImpl<Customer> pageImpl = new PageImpl<>(new ArrayList<>());
        when(customerRepository.findAll((Pageable) any())).thenReturn(pageImpl);
        Page<Customer> actualAllCustomers = customerService.getAllCustomers(null);
        assertSame(pageImpl, actualAllCustomers);
        assertTrue(actualAllCustomers.toList().isEmpty());
        verify(customerRepository).findAll((Pageable) any());
    }

    /**
     * Method under test: {@link CustomerService#getAllCustomers(Pageable)}
     */
    @Test
    void testGetAllCustomers2() {
        when(customerRepository.findAll((Pageable) any())).thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> customerService.getAllCustomers(null));
        verify(customerRepository).findAll((Pageable) any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomersWithoutTransactions(Pageable)}
     */
    @Test
    void testGetCustomersWithoutTransactions() {
        PageImpl<Customer> pageImpl = new PageImpl<>(new ArrayList<>());
        when(customerRepository.findCustomerByTransactionsLessThan0(any())).thenReturn(pageImpl);
        Page<Customer> actualCustomersWithoutTransactions = customerService.getCustomersWithoutTransactions(null);
        assertSame(pageImpl, actualCustomersWithoutTransactions);
        assertTrue(actualCustomersWithoutTransactions.toList().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomersWithoutTransactions(Pageable)}
     */
    @Test
    void testGetCustomersWithoutTransactions2() {
        when(customerRepository.findCustomerByTransactionsLessThan0(any()))
            .thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomersWithoutTransactions(null));
        verify(customerRepository).findCustomerByTransactionsLessThan0(any());
    }

    /**
     * Method under test: {@link CustomerService#findCustomerByTransactions(Pageable)}
     */
    @Test
    void testFindCustomerByTransactions() {
        PageImpl<Customer> pageImpl = new PageImpl<>(new ArrayList<>());
        when(customerRepository.findAllCustomersByTransactions(any())).thenReturn(pageImpl);
        Page<Customer> actualFindCustomerByTransactionsResult = customerService.findCustomerByTransactions(null);
        assertSame(pageImpl, actualFindCustomerByTransactionsResult);
        assertTrue(actualFindCustomerByTransactionsResult.toList().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions(any());
    }

    /**
     * Method under test: {@link CustomerService#findCustomerByTransactions(Pageable)}
     */
    @Test
    void testFindCustomerByTransactions2() {
        when(customerRepository.findAllCustomersByTransactions(any()))
            .thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> customerService.findCustomerByTransactions(null));
        verify(customerRepository).findAllCustomersByTransactions(any());
    }

    /**
     * Method under test: {@link CustomerService#getTopInactiveCustomersByYears(int, int)}
     */
    @Test
    void testGetTopInactiveCustomersByYears() throws DataAccessException {
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        when(namedParameterJdbcTemplate.queryForList(any(), (SqlParameterSource) any())).thenReturn(mapList);
        List<Map<String, Object>> actualTopInactiveCustomersByYears = customerService.getTopInactiveCustomersByYears(1,
            1);
        assertSame(mapList, actualTopInactiveCustomersByYears);
        assertTrue(actualTopInactiveCustomersByYears.isEmpty());
        verify(namedParameterJdbcTemplate).queryForList(any(), (SqlParameterSource) any());
    }
}

