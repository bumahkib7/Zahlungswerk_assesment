package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.Dto.TopInactiveCustomerDTO;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/Customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @GetMapping("/without-transactions")
    public ResponseEntity<List<Customer>> getCustomersWithoutTransactions() {
        List<Customer> customers = customerService.getCustomersWithoutTransactions();
        return customers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(customers);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return customers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(customers);
    }

    @GetMapping("/with-transactions")
    public ResponseEntity<List<Customer>> getAllCustomersWithTransactions() {
        List<Customer> customers = customerService.findCustomerByTransactions();
        return customers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(customers);
    }

    @GetMapping("/inactive-customers")
    public ResponseEntity<List<TopInactiveCustomerDTO>> getTopInactiveCustomers() {
        List<TopInactiveCustomerDTO> inactiveCustomers = customerService.getTopInactiveCustomers();
        return inactiveCustomers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inactiveCustomers);
    }


}
