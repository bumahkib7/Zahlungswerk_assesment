package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    /**
     * Method under test: {@link CustomerService.CustomerRawMapper#mapRow(ResultSet, int)}
     */
    @Test
    public void testCustomerRawMapperMapRow() throws SQLException {
        // Arrange
        CustomerService.CustomerRawMapper customerRawMapper = new CustomerService.CustomerRawMapper();
        Date date = mock(Date.class);
        when(date.toLocalDate()).thenReturn(LocalDate.ofEpochDay(1L));
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getBoolean(any())).thenReturn(true);
        when(resultSet.getLong(any())).thenReturn(1L);
        when(resultSet.getDate(any())).thenReturn(date);
        when(resultSet.getInt(any())).thenReturn(1);
        when(resultSet.getString(any())).thenReturn("String");
        int rowNum = 10;

        // Act
        Object[] actualMapRowResult = customerRawMapper.mapRow(resultSet, rowNum);

        // Assert
        assertEquals(2, actualMapRowResult.length);
        verify(resultSet).getBoolean(any());
        verify(resultSet).getInt(any());
        verify(resultSet, atLeast(1)).getString(any());
        verify(resultSet).getDate(any());
        verify(resultSet).getLong(any());
        verify(date).toLocalDate();
    }

    /**
     * Method under test: {@link CustomerService#getAllCustomers()}
     */
    @Test
    public void testGetAllCustomers() {
        // Arrange
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customerList);

        // Act
        List<Customer> actualAllCustomers = customerService.getAllCustomers();

        // Assert
        assertSame(customerList, actualAllCustomers);
        assertTrue(actualAllCustomers.isEmpty());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getAllCustomers()}
     */
    @Test
    public void testGetAllCustomers2() {
        // Arrange
        when(customerRepository.findAll()).thenThrow(new EntityNotFoundException("An error occurred"));

        // Act
        List<Customer> actualAllCustomers = customerService.getAllCustomers();

        // Assert
        assertNull(actualAllCustomers);
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById() {
        // Arrange
        Customer customer = new Customer();
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        long id = 1L;

        // Act
        Customer actualCustomerById = customerService.getCustomerById(id);

        // Assert
        assertSame(customer, actualCustomerById);
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById2() {
        // Arrange
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        long id = 1L;

        // Act
        Customer actualCustomerById = customerService.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById);
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById3() {
        // Arrange
        when(customerRepository.findById(any())).thenThrow(new EntityNotFoundException("An error occurred"));
        long id = 1L;

        // Act
        Customer actualCustomerById = customerService.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById);
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById4() {
        // Arrange
        Customer customer = new Customer();
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        long id = 1L;

        // Act
        Customer actualCustomerById = customerService.getCustomerById(id);

        // Assert
        assertSame(customer, actualCustomerById);
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById5() {
        // Arrange
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        long id = 1L;

        // Act
        Customer actualCustomerById = customerService.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById);
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomerById(Long)}
     */
    @Test
    public void testGetCustomerById6() {
        // Arrange
        when(customerRepository.findById(any())).thenThrow(new EntityNotFoundException("An error occurred"));
        long id = 1L;

        // Act
        Customer actualCustomerById = customerService.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById);
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomersWithoutTransactions()}
     */
    @Test
    public void testGetCustomersWithoutTransactions() {
        // Arrange
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findCustomerByTransactionsLessThan0()).thenReturn(customerList);

        // Act
        List<Customer> actualCustomersWithoutTransactions = customerService.getCustomersWithoutTransactions();

        // Assert
        assertSame(customerList, actualCustomersWithoutTransactions);
        assertTrue(actualCustomersWithoutTransactions.isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

    /**
     * Method under test: {@link CustomerService#getCustomersWithoutTransactions()}
     */
    @Test
    public void testGetCustomersWithoutTransactions2() {
        // Arrange
        when(customerRepository.findCustomerByTransactionsLessThan0())
            .thenThrow(new EntityNotFoundException("An error occurred"));

        // Act
        List<Customer> actualCustomersWithoutTransactions = customerService.getCustomersWithoutTransactions();

        // Assert
        assertNull(actualCustomersWithoutTransactions);
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

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

    /**
     * Method under test: {@link CustomerService#getTopInactiveCustomers()}
     */
    @Test
    public void testGetTopInactiveCustomers() throws DataAccessException {
        // Arrange
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (RowMapper<Object[]>) any())).thenReturn(objectArrayList);

        // Act
        List<Object[]> actualTopInactiveCustomers = customerService.getTopInactiveCustomers();

        // Assert
        assertSame(objectArrayList, actualTopInactiveCustomers);
        assertTrue(actualTopInactiveCustomers.isEmpty());
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object[]>) any());
    }

    /**
     * Method under test: {@link CustomerService#getTopInactiveCustomers()}
     */
    @Test
    public void testGetTopInactiveCustomers2() throws DataAccessException {
        // Arrange
        when(jdbcTemplate.query((String) any(), (RowMapper<Object[]>) any()))
            .thenThrow(new EntityNotFoundException("An error occurred"));

        // Act
        List<Object[]> actualTopInactiveCustomers = customerService.getTopInactiveCustomers();

        // Assert
        assertNull(actualTopInactiveCustomers);
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object[]>) any());
    }
}

