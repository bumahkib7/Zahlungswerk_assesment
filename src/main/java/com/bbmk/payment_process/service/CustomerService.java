package com.bbmk.payment_process.service;

import com.bbmk.payment_process.Dto.TopInactiveCustomerDTO;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, JdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public List<Customer> getCustomersWithoutTransactions() {
        return customerRepository.findCustomerByTransactionsLessThan0();
    }

    public List<Customer> findCustomerByTransactions() {
        return customerRepository.findAllCustomersByTransactions();
    }

    public List<TopInactiveCustomerDTO> getTopInactiveCustomers() {
        String sql = """
            SELECT customer_id, COUNT(*) as num_transactions
            FROM payment_transaction
            WHERE EXTRACT(YEAR FROM transaction_date) = 2022
            AND customer_id NOT IN (SELECT customer_id FROM payment_transaction WHERE EXTRACT(YEAR FROM transaction_date) = 2023)
            GROUP BY customer_id
            ORDER BY num_transactions DESC
            LIMIT 5;
            """;
        return jdbcTemplate.query(sql, new CustomerRawMapper()).stream()
            .map(row -> {
                Customer customer = (Customer) row[0];
                Long numTransactions = (Long) row[1];
                return new TopInactiveCustomerDTO(customer.getId(), numTransactions);
            })
            .collect(Collectors.toList());
    }

    public List<Customer> getInactiveCustomers() {
        Year currentYear = Year.now();
        Year previousYear = currentYear.minusYears(1);

        return customerRepository.findAll().stream()
            .filter(customer -> customerRepository.findCustomerTransactionsByYear(customer.getId(), previousYear) > 0)
            .filter(customer -> customerRepository.findCustomerTransactionsByYear(customer.getId(), currentYear) == 0)
            .collect(Collectors.toList());
    }

    public static class CustomerRawMapper implements RowMapper<Object[]> {
        @Override
        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer(
                rs.getInt("customer_id"),
                Base64.getEncoder().encodeToString(rs.getString("name").getBytes()),
                Base64.getEncoder().encodeToString(rs.getString("email").getBytes()),
                rs.getDate("date_of_registration").toLocalDate(),
                rs.getBoolean("is_active")
            );
            long numTransactions = rs.getLong("num_transactions");
            return new Object[]{customer, numTransactions};
        }
    }
}
