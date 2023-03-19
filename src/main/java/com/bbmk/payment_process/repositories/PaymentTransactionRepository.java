package com.bbmk.payment_process.repositories;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import org.hibernate.NonUniqueResultException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {


    List<PaymentTransaction> findByMerchantId(Long merchantId);

    List<PaymentTransaction> findByCustomerId(Long customerId);




    @Query("SELECT pt FROM PaymentTransaction pt WHERE pt.customer.id = :customerId AND pt.merchant.id = :merchantId")
    PaymentTransaction findCustomerAndMerchantById(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);

    @Query("SELECT p FROM PaymentTransaction p WHERE p.merchant.id = :merchantId")
    List<PaymentTransaction> findPaymentTransactionByMerchantId(@Param("merchantId") Long merchantId);

    @Query("SELECT m FROM Merchant m JOIN m.transactions pt WHERE YEAR(pt.transactionDate) = :year AND pt.grossAmount > 0 GROUP BY m.id ORDER BY SUM(pt.grossAmount) DESC")
    Merchant getMerchantWithHighestTurnoverinYear(@Param("year") int year) throws NonUniqueResultException;

    @Query("SELECT pt.merchant, SUM(pt.grossAmount) AS turnover FROM PaymentTransaction pt WHERE YEAR(pt.transactionDate) = 2022 GROUP BY pt.merchant.id ORDER BY turnover DESC")
    Merchant getMerchantWithHighestTurnover() throws NonUniqueResultException;


}

