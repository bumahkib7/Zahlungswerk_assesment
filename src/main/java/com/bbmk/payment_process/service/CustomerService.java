package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.repositories.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EntityManager entityManager;

    public CustomerService(CustomerRepository customerRepository, EntityManager entityManager) {
        this.customerRepository = customerRepository;
        this.entityManager = entityManager;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        try {
            return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        } catch (EntityNotFoundException e) {
            log.error("Customer not found");
            return null;
        }

    }

    public List<Customer> getCustomersWithoutTransactions() {
        return customerRepository.findCustomerByTransactionsLessThan0();
    }

    @Transactional
    public List<Customer> getTopInactiveCustomers(LocalDate endDate) {
        try {
            String hql = "SELECT t.customer, COUNT(DISTINCT t.customer) as transaction_count FROM PaymentTransaction t WHERE YEAR(t.transactionDate) = :year AND t.customer.isActive = false GROUP BY t.customer HAVING MIN(t.transactionDate) < :endDate ORDER BY transaction_count DESC";
            TypedQuery<Customer> query = entityManager.createQuery(hql, Customer.class);
            query.setParameter("endDate", endDate);
            query.setParameter("year", endDate.getYear() - 1);
            query.setMaxResults(5);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error in getTop InactiveCustomers:" + e.getMessage());
            return null;
        }
    }

}
