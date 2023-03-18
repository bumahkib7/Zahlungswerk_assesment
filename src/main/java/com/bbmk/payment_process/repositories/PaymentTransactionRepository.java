package com.example.zahlungswerk.repositories;

import com.example.zahlungswerk.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    List<PaymentTransaction> findByMerchantId(Long merchantId);

    List<PaymentTransaction> findByCustomerId(Long customerId);

    List<PaymentTransaction> findByMerchantIdAndTransactionDateBetween(Long merchant_id, LocalDate transactionDate, LocalDate transactionDate2);

    @Query("SELECT t.merchant, SUM(t.grossAmount) FROM PaymentTransaction t WHERE YEAR(t.transactionDate) = :year GROUP BY t.merchant ORDER BY SUM(t.grossAmount) DESC")
    List<Object[]> findMerchantWithHighestTurnover(@Param("year") Integer year);
}

