package com.bbmk.payment_process.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {CustomerService.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    /**
     * Method under test: {@link CustomerService#findCustomerByTransactions()}
     */
    @Test
    public void testFindCustomerByTransactions() {
        // Arrange
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAllCustomersByTransactions()).thenReturn(customerList);

        // Act
        List<Customer> actualFindCustomerByTransactionsResult = customerService.findCustomerByTransactions();

        // Assert
        assertSame(customerList, actualFindCustomerByTransactionsResult);
        assertTrue(actualFindCustomerByTransactionsResult.isEmpty());
        verify(customerRepository).findAllCustomersByTransactions();
    }

    /**
     * Method under test: {@link CustomerService#findCustomerByTransactions()}
     */
    @Test
    public void testFindCustomerByTransactions2() {
        // Arrange
        when(customerRepository.findAllCustomersByTransactions())
            .thenThrow(new EntityNotFoundException("An error occurred"));

        // Act
        List<Customer> actualFindCustomerByTransactionsResult = customerService.findCustomerByTransactions();

        // Assert
        assertNull(actualFindCustomerByTransactionsResult);
        verify(customerRepository).findAllCustomersByTransactions();
    }
}

