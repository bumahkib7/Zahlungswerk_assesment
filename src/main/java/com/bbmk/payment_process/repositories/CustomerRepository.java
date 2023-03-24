package com.bbmk.payment_process.repositories;

import com.bbmk.payment_process.models.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @NotNull Page<Customer> findAll(@NotNull Pageable pageable);

    @NotNull
    Optional<Customer> findById(@NotNull Long id);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN c.transactions t WHERE t.id IS NULL ORDER BY c.name")
    Page<Customer> findCustomerByTransactionsLessThan0(Pageable pageable);

    @Query("SELECT DISTINCT c FROM Customer c INNER JOIN c.transactions t WHERE t.customer = c ORDER BY c.name")
    Page<Customer> findAllCustomersByTransactions(Pageable pageable);

}
