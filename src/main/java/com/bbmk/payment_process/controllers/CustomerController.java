package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.Dto.CustomerDTO;
import com.bbmk.payment_process.models.Customer;
import com.bbmk.payment_process.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * The type Customer controller.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/Customer")
@RestControllerAdvice
public class CustomerController {
    private final CustomerService customerService;

    /**
     * Instantiates a new Customer controller.
     *
     * @param customerService the customer service
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.findCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/without-transactions")
    public ResponseEntity<Page<Customer>> getCustomersWithoutTransactions(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerService.getCustomersWithoutTransactions(pageable);
        return customerPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(customerPage);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<Customer>> getAllCustomers(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "2") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customerPage);
    }

    @GetMapping("/with-transactions")
    public ResponseEntity<Page<Customer>> findCustomerByTransactions(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerService.findCustomerByTransactions(pageable);
        return customerPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(customerPage);
    }


    @GetMapping("/top-inactive-customers-by-years")
    public ResponseEntity<List<Map<String, Object>>> getTopInactiveCustomersByYears(
        @RequestParam("activeYear") int activeYear,
        @RequestParam("inactiveYear") int inactiveYear) {

        List<Map<String, Object>> topInactiveCustomers =
            customerService.getTopInactiveCustomersByYears(activeYear, inactiveYear);
        return ResponseEntity.ok(topInactiveCustomers);
    }


}
