package com.bbmk.payment_process.repositories;

import com.bbmk.payment_process.models.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {


    List<PaymentTransaction> findByMerchantId(Long merchantId);

    List<PaymentTransaction> findByCustomerId(Long customerId);


    @Query("SELECT pt FROM PaymentTransaction pt WHERE pt.customer.id = :customerId AND pt.merchant.id = :merchantId")
    PaymentTransaction findCustomerAndMerchantById(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);

    @Query("SELECT p FROM PaymentTransaction p WHERE p.merchant.id = :merchantId")
    List<PaymentTransaction> findPaymentTransactionByMerchantId(@Param("merchantId") Long merchantId);


    boolean existsById(UUID uuid);

    void deleteById(UUID id);
}

