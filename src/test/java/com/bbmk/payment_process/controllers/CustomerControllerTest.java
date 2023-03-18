package com.bbmk.payment_process.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.service.CustomerService;

import java.util.ArrayList;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

class CustomerControllerTest {
    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<?> actualAllCustomers = customerController.getAllCustomers();

        // Assert
        assertNull(actualAllCustomers.getBody());
        assertEquals(204, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenReturn(customerList);
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<?> actualAllCustomers = customerController.getAllCustomers();

        // Assert
        assertTrue(actualAllCustomers.hasBody());
        assertEquals(200, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerRepository).findAll();
    }
}

