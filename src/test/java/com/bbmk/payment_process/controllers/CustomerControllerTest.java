package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.Dto.TopInactiveCustomerDTO;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.service.CustomerService;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {
    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));
        ResponseEntity<Customer> actualCustomerById = (new CustomerController(
            new CustomerService(customerRepository, mock(JdbcTemplate.class)))).getCustomerById(1L);
        assertTrue(actualCustomerById.hasBody());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        assertEquals(200, actualCustomerById.getStatusCodeValue());
        verify(customerRepository).findById(any());
    }


    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById4() {

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomerById(any())).thenReturn(new Customer());
        ResponseEntity<Customer> actualCustomerById = (new CustomerController(customerService)).getCustomerById(1L);
        assertTrue(actualCustomerById.hasBody());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        assertEquals(200, actualCustomerById.getStatusCodeValue());
        verify(customerService).getCustomerById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById5() {

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomerById(any())).thenReturn(null);
        ResponseEntity<Customer> actualCustomerById = (new CustomerController(customerService)).getCustomerById(1L);
        assertNull(actualCustomerById.getBody());
        assertEquals(404, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        verify(customerService).getCustomerById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    public void testGetCustomersWithoutTransactions() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findCustomerByTransactionsLessThan0()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = (new CustomerController(
            new CustomerService(customerRepository, mock(JdbcTemplate.class)))).getCustomersWithoutTransactions();
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(204, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    public void testGetCustomersWithoutTransactions2() {

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findCustomerByTransactionsLessThan0()).thenReturn(customerList);
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = (new CustomerController(
            new CustomerService(customerRepository, mock(JdbcTemplate.class)))).getCustomersWithoutTransactions();
        assertTrue(actualCustomersWithoutTransactions.hasBody());
        assertEquals(200, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }


    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    public void testGetCustomersWithoutTransactions4() {

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomersWithoutTransactions()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = (new CustomerController(customerService))
            .getCustomersWithoutTransactions();
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(204, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerService).getCustomersWithoutTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    public void testGetAllCustomers() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Customer>> actualAllCustomers = (new CustomerController(
            new CustomerService(customerRepository, mock(JdbcTemplate.class)))).getAllCustomers();
        assertNull(actualAllCustomers.getBody());
        assertEquals(204, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    public void testGetAllCustomers2() {

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenReturn(customerList);
        ResponseEntity<List<Customer>> actualAllCustomers = (new CustomerController(
            new CustomerService(customerRepository, mock(JdbcTemplate.class)))).getAllCustomers();
        assertTrue(actualAllCustomers.hasBody());
        assertEquals(200, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerRepository).findAll();
    }


    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    public void testGetAllCustomers4() {

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getAllCustomers()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Customer>> actualAllCustomers = (new CustomerController(customerService)).getAllCustomers();
        assertNull(actualAllCustomers.getBody());
        assertEquals(204, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerService).getAllCustomers();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    public void testGetAllCustomersWithTransactions() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAllCustomersByTransactions()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Customer>> actualAllCustomersWithTransactions = (new CustomerController(
            new CustomerService(customerRepository, mock(JdbcTemplate.class)))).getAllCustomersWithTransactions();
        assertNull(actualAllCustomersWithTransactions.getBody());
        assertEquals(204, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    public void testGetAllCustomersWithTransactions2() {

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAllCustomersByTransactions()).thenReturn(customerList);
        ResponseEntity<List<Customer>> actualAllCustomersWithTransactions = (new CustomerController(
            new CustomerService(customerRepository, mock(JdbcTemplate.class)))).getAllCustomersWithTransactions();
        assertTrue(actualAllCustomersWithTransactions.hasBody());
        assertEquals(200, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions();
    }


    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    public void testGetAllCustomersWithTransactions4() {

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.findCustomerByTransactions()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Customer>> actualAllCustomersWithTransactions = (new CustomerController(customerService))
            .getAllCustomersWithTransactions();
        assertNull(actualAllCustomersWithTransactions.getBody());
        assertEquals(204, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerService).findCustomerByTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    public void testGetTopInactiveCustomers() throws DataAccessException {

        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        when(jdbcTemplate.query((String) any(), (RowMapper<Object[]>) any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<TopInactiveCustomerDTO>> actualTopInactiveCustomers = (new CustomerController(
            new CustomerService(mock(CustomerRepository.class), jdbcTemplate))).getTopInactiveCustomers();
        assertNull(actualTopInactiveCustomers.getBody());
        assertEquals(204, actualTopInactiveCustomers.getStatusCodeValue());
        assertTrue(actualTopInactiveCustomers.getHeaders().isEmpty());
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object[]>) any());
    }


    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    public void testGetTopInactiveCustomers3() {

        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getTopInactiveCustomers()).thenReturn(new ArrayList<>());
        ResponseEntity<List<TopInactiveCustomerDTO>> actualTopInactiveCustomers = (new CustomerController(
            customerService)).getTopInactiveCustomers();
        assertNull(actualTopInactiveCustomers.getBody());
        assertEquals(204, actualTopInactiveCustomers.getStatusCodeValue());
        assertTrue(actualTopInactiveCustomers.getHeaders().isEmpty());
        verify(customerService).getTopInactiveCustomers();
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    public void testGetTopInactiveCustomers4() {

        ArrayList<TopInactiveCustomerDTO> topInactiveCustomerDTOList = new ArrayList<>();
        topInactiveCustomerDTOList.add(new TopInactiveCustomerDTO());
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getTopInactiveCustomers()).thenReturn(topInactiveCustomerDTOList);
        ResponseEntity<List<TopInactiveCustomerDTO>> actualTopInactiveCustomers = (new CustomerController(
            customerService)).getTopInactiveCustomers();
        assertTrue(actualTopInactiveCustomers.hasBody());
        assertTrue(actualTopInactiveCustomers.getHeaders().isEmpty());
        assertEquals(200, actualTopInactiveCustomers.getStatusCodeValue());
        verify(customerService).getTopInactiveCustomers();
    }
}

