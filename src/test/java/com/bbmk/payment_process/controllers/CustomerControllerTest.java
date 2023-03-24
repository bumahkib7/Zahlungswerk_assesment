package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.service.CustomerService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions(Integer, Integer)}
     */
    @Test
    void testGetCustomersWithoutTransactions() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findCustomerByTransactionsLessThan0(any()))
            .thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<Customer>> actualCustomersWithoutTransactions = (new CustomerController(
            new CustomerService(customerRepository, mock(NamedParameterJdbcTemplate.class))))
            .getCustomersWithoutTransactions(1, 3);
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(204, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions(Integer, Integer)}
     */
    @Test
    void testGetCustomersWithoutTransactions2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findCustomerByTransactionsLessThan0(any()))
            .thenReturn(new PageImpl<>(customerList));
        ResponseEntity<Page<Customer>> actualCustomersWithoutTransactions = (new CustomerController(
            new CustomerService(customerRepository, mock(NamedParameterJdbcTemplate.class))))
            .getCustomersWithoutTransactions(1, 3);
        assertTrue(actualCustomersWithoutTransactions.hasBody());
        assertEquals(200, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertEquals(1, actualCustomersWithoutTransactions.getBody().toList().size());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions(Integer, Integer)}
     */
    @Test
    void testGetCustomersWithoutTransactions3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomersWithoutTransactions(any()))
            .thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<Customer>> actualCustomersWithoutTransactions = (new CustomerController(customerService))
            .getCustomersWithoutTransactions(1, 3);
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(204, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerService).getCustomersWithoutTransactions(any());
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers(Integer, Integer)}
     */
    @Test
    void testGetAllCustomers() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<Customer>> actualAllCustomers = (new CustomerController(
            new CustomerService(customerRepository, mock(NamedParameterJdbcTemplate.class)))).getAllCustomers(1, 3);
        assertTrue(actualAllCustomers.hasBody());
        assertEquals(200, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getBody().toList().isEmpty());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerRepository).findAll((Pageable) any());
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers(Integer, Integer)}
     */
    @Test
    void testGetAllCustomers2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getAllCustomers(any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<Customer>> actualAllCustomers = (new CustomerController(customerService)).getAllCustomers(1,
            3);
        assertTrue(actualAllCustomers.hasBody());
        assertEquals(200, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getBody().toList().isEmpty());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerService).getAllCustomers(any());
    }

    /**
     * Method under test: {@link CustomerController#findCustomerByTransactions(Integer, Integer)}
     */
    @Test
    void testFindCustomerByTransactions() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAllCustomersByTransactions(any()))
            .thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<Customer>> actualFindCustomerByTransactionsResult = (new CustomerController(
            new CustomerService(customerRepository, mock(NamedParameterJdbcTemplate.class)))).findCustomerByTransactions(1,
            3);
        assertNull(actualFindCustomerByTransactionsResult.getBody());
        assertEquals(204, actualFindCustomerByTransactionsResult.getStatusCodeValue());
        assertTrue(actualFindCustomerByTransactionsResult.getHeaders().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions(any());
    }

    /**
     * Method under test: {@link CustomerController#findCustomerByTransactions(Integer, Integer)}
     */
    @Test
    void testFindCustomerByTransactions2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAllCustomersByTransactions(any()))
            .thenReturn(new PageImpl<>(customerList));
        ResponseEntity<Page<Customer>> actualFindCustomerByTransactionsResult = (new CustomerController(
            new CustomerService(customerRepository, mock(NamedParameterJdbcTemplate.class))))
            .findCustomerByTransactions(1, 3);
        assertTrue(actualFindCustomerByTransactionsResult.hasBody());
        assertEquals(200, actualFindCustomerByTransactionsResult.getStatusCodeValue());
        assertEquals(1, actualFindCustomerByTransactionsResult.getBody().toList().size());
        assertTrue(actualFindCustomerByTransactionsResult.getHeaders().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions(any());
    }

    /**
     * Method under test: {@link CustomerController#findCustomerByTransactions(Integer, Integer)}
     */
    @Test
    void testFindCustomerByTransactions3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.findCustomerByTransactions(any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<Customer>> actualFindCustomerByTransactionsResult = (new CustomerController(customerService))
            .findCustomerByTransactions(1, 3);
        assertNull(actualFindCustomerByTransactionsResult.getBody());
        assertEquals(204, actualFindCustomerByTransactionsResult.getStatusCodeValue());
        assertTrue(actualFindCustomerByTransactionsResult.getHeaders().isEmpty());
        verify(customerService).findCustomerByTransactions(any());
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomersByYears(int, int)}
     */
    @Test
    void testGetTopInactiveCustomersByYears() throws DataAccessException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        when(namedParameterJdbcTemplate.queryForList(any(), (SqlParameterSource) any()))
            .thenReturn(new ArrayList<>());
        ResponseEntity<List<Map<String, Object>>> actualTopInactiveCustomersByYears = (new CustomerController(
            new CustomerService(mock(CustomerRepository.class), namedParameterJdbcTemplate)))
            .getTopInactiveCustomersByYears(1, 1);
        assertTrue(actualTopInactiveCustomersByYears.hasBody());
        assertEquals(200, actualTopInactiveCustomersByYears.getStatusCodeValue());
        assertTrue(actualTopInactiveCustomersByYears.getHeaders().isEmpty());
        verify(namedParameterJdbcTemplate).queryForList(any(), (SqlParameterSource) any());
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomersByYears(int, int)}
     */
    @Test
    void testGetTopInactiveCustomersByYears2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getTopInactiveCustomersByYears(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Map<String, Object>>> actualTopInactiveCustomersByYears = (new CustomerController(
            customerService)).getTopInactiveCustomersByYears(1, 1);
        assertTrue(actualTopInactiveCustomersByYears.hasBody());
        assertEquals(200, actualTopInactiveCustomersByYears.getStatusCodeValue());
        assertTrue(actualTopInactiveCustomersByYears.getHeaders().isEmpty());
        verify(customerService).getTopInactiveCustomersByYears(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetCustomerById() throws Exception {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        Object[] uriVariables = new Object[]{1L};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/Customer/{id}", uriVariables);
        Object[] controllers = new Object[]{customerController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        // Assert
        // TODO: Add assertions on result
    }
}

