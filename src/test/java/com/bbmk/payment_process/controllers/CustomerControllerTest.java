package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.bbmk.payment_process.service.CustomerService;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)), mock(JdbcTemplate.class)));
        Long id = null;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(404, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById2() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));
        long id = 1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(500, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById3() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer(1L,
            "@NotNull method %s.%s must not return null", "jane.doe@example.org", LocalDate.ofEpochDay(1L), true)));
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));
        long id = 1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertTrue(actualCustomerById.hasBody());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        assertEquals(200, actualCustomerById.getStatusCodeValue());
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById4() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(any())).thenReturn(null);
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));
        long id = 1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(404, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById5() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));
        long id = 1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(404, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        verify(customerRepository).findById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById6() {

        // Arrange
        CustomerController customerController = new CustomerController(null);
        long id = 1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(500, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById7() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomerById(any())).thenReturn(new Customer());
        CustomerController customerController = new CustomerController(customerService);
        long id = 1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(500, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        verify(customerService).getCustomerById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById8() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomerById(any())).thenReturn(new Customer());
        CustomerController customerController = new CustomerController(customerService);
        long id = -1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(404, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(Long)}
     */
    @Test
    void testGetCustomerById9() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomerById(any())).thenThrow(new IllegalArgumentException());
        CustomerController customerController = new CustomerController(customerService);
        long id = 1L;

        // Act
        ResponseEntity<Customer> actualCustomerById = customerController.getCustomerById(id);

        // Assert
        assertNull(actualCustomerById.getBody());
        assertEquals(400, actualCustomerById.getStatusCodeValue());
        assertTrue(actualCustomerById.getHeaders().isEmpty());
        verify(customerService).getCustomerById(any());
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    void testGetCustomersWithoutTransactions() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findCustomerByTransactionsLessThan0()).thenReturn(new ArrayList<>());
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = customerController
            .getCustomersWithoutTransactions();

        // Assert
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(204, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    void testGetCustomersWithoutTransactions2() {

        // Arrange
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findCustomerByTransactionsLessThan0()).thenReturn(customerList);
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = customerController
            .getCustomersWithoutTransactions();

        // Assert
        assertTrue(actualCustomersWithoutTransactions.hasBody());
        assertEquals(200, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    void testGetCustomersWithoutTransactions3() {

        // Arrange
        CustomerController customerController = new CustomerController(null);

        // Act
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = customerController
            .getCustomersWithoutTransactions();

        // Assert
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(500, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    void testGetCustomersWithoutTransactions4() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomersWithoutTransactions()).thenReturn(new ArrayList<>());
        CustomerController customerController = new CustomerController(customerService);

        // Act
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = customerController
            .getCustomersWithoutTransactions();

        // Assert
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(204, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerService).getCustomersWithoutTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    void testGetCustomersWithoutTransactions5() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getCustomersWithoutTransactions()).thenThrow(new IllegalArgumentException());
        CustomerController customerController = new CustomerController(customerService);

        // Act
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = customerController
            .getCustomersWithoutTransactions();

        // Assert
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(400, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerService).getCustomersWithoutTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getCustomersWithoutTransactions()}
     */
    @Test
    void testGetCustomersWithoutTransactions6() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findCustomerByTransactionsLessThan0()).thenThrow(new IllegalArgumentException());
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<List<Customer>> actualCustomersWithoutTransactions = customerController
            .getCustomersWithoutTransactions();

        // Assert
        assertNull(actualCustomersWithoutTransactions.getBody());
        assertEquals(204, actualCustomersWithoutTransactions.getStatusCodeValue());
        assertTrue(actualCustomersWithoutTransactions.getHeaders().isEmpty());
        verify(customerRepository).findCustomerByTransactionsLessThan0();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers() {

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

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers3() {

        // Arrange
        CustomerController customerController = new CustomerController(null);

        // Act
        ResponseEntity<?> actualAllCustomers = customerController.getAllCustomers();

        // Assert
        assertNull(actualAllCustomers.getBody());
        assertEquals(500, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers4() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getAllCustomers()).thenReturn(new ArrayList<>());
        CustomerController customerController = new CustomerController(customerService);

        // Act
        ResponseEntity<?> actualAllCustomers = customerController.getAllCustomers();

        // Assert
        assertNull(actualAllCustomers.getBody());
        assertEquals(204, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerService).getAllCustomers();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers5() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getAllCustomers()).thenThrow(new IllegalArgumentException());
        CustomerController customerController = new CustomerController(customerService);

        // Act
        ResponseEntity<?> actualAllCustomers = customerController.getAllCustomers();

        // Assert
        assertNull(actualAllCustomers.getBody());
        assertEquals(400, actualAllCustomers.getStatusCodeValue());
        assertTrue(actualAllCustomers.getHeaders().isEmpty());
        verify(customerService).getAllCustomers();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers6() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenThrow(new IllegalArgumentException());
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
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    void testGetTopInactiveCustomers() throws DataAccessException {

        // Arrange
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (RowMapper<Object[]>) any())).thenReturn(objectArrayList);
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            jdbcTemplate));

        // Act
        List<Object[]> actualTopInactiveCustomers = customerController.getTopInactiveCustomers();

        // Assert
        assertSame(objectArrayList, actualTopInactiveCustomers);
        assertTrue(actualTopInactiveCustomers.isEmpty());
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object[]>) any());
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    void testGetTopInactiveCustomers2() {

        // Arrange
        CustomerController customerController = new CustomerController(null);

        // Act
        List<Object[]> actualTopInactiveCustomers = customerController.getTopInactiveCustomers();

        // Assert
        assertNull(actualTopInactiveCustomers);
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    void testGetTopInactiveCustomers3() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        when(customerService.getTopInactiveCustomers()).thenReturn(objectArrayList);
        CustomerController customerController = new CustomerController(customerService);

        // Act
        List<Object[]> actualTopInactiveCustomers = customerController.getTopInactiveCustomers();

        // Assert
        assertSame(objectArrayList, actualTopInactiveCustomers);
        assertTrue(actualTopInactiveCustomers.isEmpty());
        verify(customerService).getTopInactiveCustomers();
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    void testGetTopInactiveCustomers4() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.getTopInactiveCustomers()).thenThrow(new IllegalArgumentException());
        CustomerController customerController = new CustomerController(customerService);

        // Act
        List<Object[]> actualTopInactiveCustomers = customerController.getTopInactiveCustomers();

        // Assert
        assertNull(actualTopInactiveCustomers);
        verify(customerService).getTopInactiveCustomers();
    }

    /**
     * Method under test: {@link CustomerController#getTopInactiveCustomers()}
     */
    @Test
    void testGetTopInactiveCustomers5() throws DataAccessException {

        // Arrange
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        when(jdbcTemplate.query((String) any(), (RowMapper<Object[]>) any())).thenThrow(new IllegalArgumentException());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            jdbcTemplate));

        // Act
        List<Object[]> actualTopInactiveCustomers = customerController.getTopInactiveCustomers();

        // Assert
        assertNull(actualTopInactiveCustomers);
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object[]>) any());
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    void testGetAllCustomersWithTransactions() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAllCustomersByTransactions()).thenReturn(new ArrayList<>());
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<?> actualAllCustomersWithTransactions = customerController.getAllCustomersWithTransactions();

        // Assert
        assertNull(actualAllCustomersWithTransactions.getBody());
        assertEquals(204, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    void testGetAllCustomersWithTransactions2() {

        // Arrange
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAllCustomersByTransactions()).thenReturn(customerList);
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<?> actualAllCustomersWithTransactions = customerController.getAllCustomersWithTransactions();

        // Assert
        assertTrue(actualAllCustomersWithTransactions.hasBody());
        assertEquals(200, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    void testGetAllCustomersWithTransactions3() {

        // Arrange
        CustomerController customerController = new CustomerController(null);

        // Act
        ResponseEntity<?> actualAllCustomersWithTransactions = customerController.getAllCustomersWithTransactions();

        // Assert
        assertNull(actualAllCustomersWithTransactions.getBody());
        assertEquals(500, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    void testGetAllCustomersWithTransactions4() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.findCustomerByTransactions()).thenReturn(new ArrayList<>());
        CustomerController customerController = new CustomerController(customerService);

        // Act
        ResponseEntity<?> actualAllCustomersWithTransactions = customerController.getAllCustomersWithTransactions();

        // Assert
        assertNull(actualAllCustomersWithTransactions.getBody());
        assertEquals(204, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerService).findCustomerByTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    void testGetAllCustomersWithTransactions5() {

        // Arrange
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.findCustomerByTransactions()).thenThrow(new IllegalArgumentException());
        CustomerController customerController = new CustomerController(customerService);

        // Act
        ResponseEntity<?> actualAllCustomersWithTransactions = customerController.getAllCustomersWithTransactions();

        // Assert
        assertNull(actualAllCustomersWithTransactions.getBody());
        assertEquals(400, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerService).findCustomerByTransactions();
    }

    /**
     * Method under test: {@link CustomerController#getAllCustomersWithTransactions()}
     */
    @Test
    void testGetAllCustomersWithTransactions6() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAllCustomersByTransactions()).thenThrow(new IllegalArgumentException());
        CustomerController customerController = new CustomerController(new CustomerService(customerRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))),
            mock(JdbcTemplate.class)));

        // Act
        ResponseEntity<?> actualAllCustomersWithTransactions = customerController.getAllCustomersWithTransactions();

        // Assert
        assertNull(actualAllCustomersWithTransactions.getBody());
        assertEquals(204, actualAllCustomersWithTransactions.getStatusCodeValue());
        assertTrue(actualAllCustomersWithTransactions.getHeaders().isEmpty());
        verify(customerRepository).findAllCustomersByTransactions();
    }
}

