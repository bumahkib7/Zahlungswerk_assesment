package com.example.zahlungswerk.repositories;

import com.example.zahlungswerk.models.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @NotNull List<Customer> findAll();

    @NotNull Optional<Customer> findById(@NotNull Long id);

    @Query("SELECT c FROM Customer c LEFT JOIN c.transactions t WHERE t.id IS NULL ORDER BY c.name")
    List<Customer> findCustomerWithoutTransactions();

   //Query to find the top 5 customers with most transactions in 2022 and no transactions in 2023
@Query("SELECT c FROM Customer c WHERE c.id IN (" +
    //Subquery to get customer IDs with transactions in 2022
    "SELECT t.customer.id FROM PaymentTransaction t WHERE t.transactionDate >= ?1 AND t.transactionDate <= ?2" +
    //Group by customer ID and having count of transactions greater than 0
    " GROUP BY t.customer.id HAVING COUNT(t.customer.id) > 0 AND NOT EXISTS (" +
    //Subquery to check if customer has any transactions in 2023
    "SELECT t2.customer.id FROM PaymentTransaction t2 WHERE t2.customer.id = t.customer.id AND t2.transactionDate >= ?3 AND t2.transactionDate <= ?4))" +
    //Order by count of transactions in descending order and limit to 5
    " ORDER BY (SELECT COUNT(t3.customer.id) FROM PaymentTransaction t3 WHERE t3.customer.id = c.id) DESC LIMIT 5")
List<Customer> findTop5ActiveCustomersWithoutTransactionsIn2023(LocalDate start2022,
LocalDate end2022,
LocalDate start2023,
LocalDate end2023);

}
