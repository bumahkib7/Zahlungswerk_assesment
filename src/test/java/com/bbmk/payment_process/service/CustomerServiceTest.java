package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerService.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    /**
     * Method under test: {@link CustomerService#getAllCustomers()}
     */
    @Test
    public void testGetAllCustomers() {
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> actualAllCustomers = customerService.getAllCustomers();
        assertSame(customerList, actualAllCustomers);
        assertTrue(actualAllCustomers.isEmpty());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getAllCustomers()}
     */
    @Test
    public void testGetAllCustomers2() {
        when(customerRepository.findAll()).thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> customerService.getAllCustomers());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById() {
        Customer customer = new Customer();
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        assertSame(customer, customerService.getCustomerById(1L));
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById2() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById3() {
        when(customerRepository.findById(any())).thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomersWithoutTransactions()}
     */
    @Test
    public void testGetCustomersWithoutTransactions() {
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findCustomerByTransactionsLessThan0()).thenReturn(customerList);
        List<Customer> actualCustomersWithoutTransactions = customerService.getCustomersWithoutTransactions();
        assertSame(customerList, actualCustomersWithoutTransactions);
        assertTrue(actualCustomersWithoutTransactions.isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

    /**
     * Method under test: {@link CustomerService#getCustomersWithoutTransactions()}
     */
    @Test
    public void testGetCustomersWithoutTransactions2() {
        when(customerRepository.findCustomerByTransactionsLessThan0())
            .thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomersWithoutTransactions());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

    /**
     * Method under test: {@link CustomerService.CustomerRawMapper#mapRow(ResultSet, int)}
     */
    @Test
    public void testCustomerRawMapperMapRow() throws SQLException {
        CustomerService.CustomerRawMapper customerRawMapper = new CustomerService.CustomerRawMapper();
        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.ofEpochDay(1L));
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getBoolean(any())).thenReturn(true);
        when(resultSet.getLong(any())).thenReturn(1L);
        when(resultSet.getDate(any())).thenReturn(date);
        when(resultSet.getInt(any())).thenReturn(1);
        when(resultSet.getString(any())).thenReturn("String");
        assertEquals(2, customerRawMapper.mapRow(resultSet, 10).length);
        verify(resultSet).getBoolean(any());
        verify(resultSet).getInt(any());
        verify(resultSet, atLeast(1)).getString(any());
        verify(resultSet).getDate(any());
        verify(resultSet).getLong(any());
        verify(date).toLocalDate();
    }

    /**
     * Method under test: {@link CustomerService#findCustomerByTransactions()}
     */
    @Test
    public void testFindCustomerByTransactions() {
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAllCustomersByTransactions()).thenReturn(customerList);
        List<Customer> actualFindCustomerByTransactionsResult = customerService.findCustomerByTransactions();
        assertSame(customerList, actualFindCustomerByTransactionsResult);
        assertTrue(actualFindCustomerByTransactionsResult.isEmpty());
        verify(customerRepository).findAllCustomersByTransactions();
    }

    /**
     * Method under test: {@link CustomerService#findCustomerByTransactions()}
     */
    @Test
    public void testFindCustomerByTransactions2() {
        when(customerRepository.findAllCustomersByTransactions())
            .thenThrow(new EntityNotFoundException("An error occurred"));
        assertThrows(EntityNotFoundException.class, () -> customerService.findCustomerByTransactions());
        verify(customerRepository).findAllCustomersByTransactions();
    }

    /**
     * Method under test: {@link CustomerService#getTopInactiveCustomers()}
     */
    @Test
    public void testGetTopInactiveCustomers() throws DataAccessException {
        when(jdbcTemplate.query((String) any(), (RowMapper<Object[]>) any())).thenReturn(new ArrayList<>());
        assertTrue(customerService.getTopInactiveCustomers().isEmpty());
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object[]>) any());
    }


    /**
     * Method under test: {@link CustomerService#getInactiveCustomers()}
     */
    @Test
    public void testGetInactiveCustomers() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(customerService.getInactiveCustomers().isEmpty());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getInactiveCustomers()}
     */
    @Test
    public void testGetInactiveCustomers2() {
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        when(customerRepository.findCustomerTransactionsByYear(any(), any())).thenReturn(1L);
        when(customerRepository.findAll()).thenReturn(customerList);
        assertTrue(customerService.getInactiveCustomers().isEmpty());
        verify(customerRepository, atLeast(1)).findCustomerTransactionsByYear(any(), any());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getInactiveCustomers()}
     */
    @Test
    public void testGetInactiveCustomers3() {
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        when(customerRepository.findCustomerTransactionsByYear(any(), any())).thenReturn(0L);
        when(customerRepository.findAll()).thenReturn(customerList);
        assertTrue(customerService.getInactiveCustomers().isEmpty());
        verify(customerRepository).findCustomerTransactionsByYear(any(), any());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getInactiveCustomers()}
     */
    @Test
    public void testGetInactiveCustomers4() {
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        customerList.add(new Customer());
        when(customerRepository.findCustomerTransactionsByYear(any(), any())).thenReturn(1L);
        when(customerRepository.findAll()).thenReturn(customerList);
        assertTrue(customerService.getInactiveCustomers().isEmpty());
        verify(customerRepository, atLeast(1)).findCustomerTransactionsByYear(any(), any());
        verify(customerRepository).findAll();
    }


    /**
     * Method under test: {@link CustomerService#getInactiveCustomers()}
     */
    @Test
    public void testGetInactiveCustomers6() {
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        when(customerRepository.findCustomerTransactionsByYear(any(), any()))
            .thenThrow(new EntityNotFoundException("An error occurred"));
        when(customerRepository.findAll()).thenReturn(customerList);
        assertThrows(EntityNotFoundException.class, () -> customerService.getInactiveCustomers());
        verify(customerRepository).findCustomerTransactionsByYear(any(), any());
        verify(customerRepository).findAll();
    }
}

