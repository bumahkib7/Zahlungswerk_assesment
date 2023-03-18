package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    @PersistenceContext
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> getAllCustomers() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            log.error("Error while fetching all customers: " + e.getMessage());
            return null;
        }
    }

    public Customer getCustomerById(Long id) {
        try {
            return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        } catch (EntityNotFoundException e) {
            log.error("Customer not found");
            return null;
        } catch (Exception e) {
            log.error("Error while fetching customer by id: " + e.getMessage());
            return null;
        }
    }

    public List<Customer> getCustomersWithoutTransactions() {
        try {
            return customerRepository.findCustomerByTransactionsLessThan0();
        } catch (Exception e) {
            log.error("Error while fetching customers without transactions: " + e.getMessage());
            return null;
        }
    }

    public List<Customer> findCustomerByTransactions() {
        try {
            return customerRepository.findAllCustomersByTransactions();
        } catch (Exception e) {
            log.error("Error while fetching customers by transactions: " + e.getMessage());
            return null;
        }
    }

    public List<Object[]> getTopInactiveCustomers() {
        String sql = """
            SELECT customer_id, COUNT(*) as num_transactions\s
            FROM payment_transaction
            WHERE EXTRACT(YEAR FROM transaction_date) = 2022\s
            AND customer_id NOT IN (SELECT customer_id FROM payment_transaction WHERE EXTRACT(YEAR FROM transaction_date) = 2023)\s
            GROUP BY customer_id\s
            ORDER BY num_transactions DESC\s
            LIMIT 5;
            """;
        try {
            return jdbcTemplate.query(
                sql,
                new CustomerRawMapper()
            );
        } catch (Exception e) {
            log.error("Error while fetching top inactive customers: " + e.getMessage());
            return null;
        }
    }

    public static class CustomerRawMapper implements RowMapper<Object[]> {
        @Override
        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
            Object[] row = new Object[2];
            row[0] = new Customer(
                rs.getInt("customer_id"),
                Base64.getEncoder().encodeToString(rs.getString("name").getBytes()),
                Base64.getEncoder().encodeToString(rs.getString("email").getBytes()),
                rs.getDate("date_of_registration").toLocalDate(),
                rs.getBoolean("is_active")
            );
            row[1] = rs.getLong("transaction_count");
            return row;
        }
    }
}
