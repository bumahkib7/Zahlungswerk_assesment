package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public Optional<Merchant> findMerchantById(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        return merchantRepository.findById(id);
    }

    public List<Merchant> getActiveMerchants() {
        TypedQuery<Merchant> query =
            entityManager.createQuery("SELECT m FROM Merchant m WHERE m.active = true", Merchant.class);
        return query.getResultList();
    }

    @Transactional
    public Merchant getMerchantWithHighestTurnoverIn2022(int year) {
        TypedQuery<Merchant> query = entityManager.createQuery(
            """
                SELECT t.merchant
                FROM PaymentTransaction t
                WHERE YEAR(t.transactionDate) = :year
                GROUP BY t.merchant
                ORDER BY SUM(t.grossAmount) DESC
                """,
            Merchant.class);
        query.setParameter("year", year);
        query.setMaxResults(1);

        return query.getSingleResult();
    }

    @Transactional
    public boolean isMerchantWithHighestTurnoverActive(int year) {
        TypedQuery<Merchant> query = entityManager.createQuery(
            """
                SELECT t.merchant FROM PaymentTransaction t WHERE YEAR(t.transactionDate) = :year GROUP BY t.merchant ORDER BY SUM(t.grossAmount) DESC
                """
            , Merchant.class);
        query.setParameter("year", year);
        query.setMaxResults(1);
        Merchant merchant = query.getSingleResult();
        return merchant.isActive();
    }
}
