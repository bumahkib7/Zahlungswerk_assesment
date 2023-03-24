package com.bbmk.payment_process.service;

import com.bbmk.payment_process.Dto.CustomerDTO;
import com.bbmk.payment_process.Dto.PaymentTransactionDTO;
import com.bbmk.payment_process.mixin.MerchantDTOMixin;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    public CustomerService(CustomerRepository customerRepository, NamedParameterJdbcTemplate jdbcTemplate) {
        this.customerRepository = customerRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public CustomerDTO findCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
            .mixIn(Merchant.class, MerchantDTOMixin.class)
            .build();

        CustomerDTO customerDTO = objectMapper.convertValue(customer, CustomerDTO.class);

        List<PaymentTransactionDTO> transactionDTOs = new ArrayList<>();
        for (PaymentTransaction transaction : customer.getTransactions()) {
            PaymentTransactionDTO transactionDTO = objectMapper.convertValue(transaction, PaymentTransactionDTO.class);
            transactionDTOs.add(transactionDTO);
        }

        customerDTO.setTransactions(transactionDTOs);

        return customerDTO;
    }


    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }


    public Page<Customer> getCustomersWithoutTransactions(Pageable pageable) {
        return customerRepository.findCustomerByTransactionsLessThan0(pageable);
    }

    public Page<Customer> findCustomerByTransactions(Pageable pageable) {
        return customerRepository.findAllCustomersByTransactions(pageable);
    }


    public List<Map<String, Object>> getTopInactiveCustomersByYears(int activeYear, int inactiveYear) {
        String sql = """
            SELECT c.name as customer_name, COUNT(*) as num_transactions
            FROM payment_transaction pt
            JOIN customer c ON pt.customer_id = c.customer_id
            LEFT JOIN (
                SELECT DISTINCT customer_id
                FROM payment_transaction
                WHERE EXTRACT(YEAR FROM transaction_date) = :inactiveYear
            ) AS pt_inactive ON c.customer_id = pt_inactive.customer_id
            WHERE EXTRACT(YEAR FROM pt.transaction_date) = :activeYear
            AND pt_inactive.customer_id IS NULL
            GROUP BY c.name
            ORDER BY num_transactions DESC
            LIMIT 5;
            """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("activeYear", activeYear);
        params.addValue("inactiveYear", inactiveYear);

        return jdbcTemplate.queryForList(sql, params);
    }
}

