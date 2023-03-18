package com.bbmk.payment_process.repositories;

import com.bbmk.payment_process.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {


    List<PaymentTransaction> findByMerchantId(Long merchantId);

    List<PaymentTransaction> findByCustomerId(Long customerId);


    @Query("SELECT t.merchant, SUM(t.grossAmount) FROM PaymentTransaction t WHERE YEAR(t.transactionDate) = :year GROUP BY t.merchant ORDER BY SUM(t.grossAmount) DESC")
    List<Object[]> findMerchantWithHighestTurnover(@Param("year") Integer year);

     @Query("SELECT pt FROM PaymentTransaction pt WHERE pt.customer.id = :customerId AND pt.merchant.id = :merchantId")
    PaymentTransaction findCustomerAndMerchantById(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);
}

