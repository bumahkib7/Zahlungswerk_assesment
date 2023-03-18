package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/customers")
@RestController
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        try {
            if (id == null || id < 0) {
                log.warn("Please provide valid id");
                return ResponseEntity.notFound().build();
            } else {
                Customer customer = customerService.getCustomerById(id);
                if (customer == null) {
                    log.warn("No customer found with id: " + id);
                    return ResponseEntity.notFound().build();
                }
                log.info("Customer found: " + customer);
                return ResponseEntity.ok(customer);
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument provided: " + e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/without-transactions")
    public ResponseEntity<List<Customer>> getCustomersWithoutTransactions() {
        try {
            List<Customer> customers = customerService.getCustomersWithoutTransactions();
            if (customers == null || customers.isEmpty()) {
                log.warn("No customers found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument provided: " + e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while retrieving customers without transactions", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            if (customers == null || customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument provided: " + e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while retrieving customers", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/inactive")
    public List<Object[]> getTopInactiveCustomers() {
        try {
            return customerService.getTopInactiveCustomers();
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument provided: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error("Error while retrieving inactive customers", e);
            return null;
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllCustomersWithTransactions() {
        try {
            List<Customer> customers = customerService.findCustomerByTransactions();
            if (customers == null || customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument provided: " + e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while retrieving customers", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
