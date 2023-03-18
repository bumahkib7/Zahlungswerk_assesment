package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;
    @PersistenceContext
    private  EntityManager entityManager;


    @Autowired
    public MerchantService(MerchantRepository merchantRepository, EntityManager entityManager) {
        this.merchantRepository = merchantRepository;
        this.entityManager = entityManager;

    }


    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;

    }

    public Optional<Merchant> findMerchantById(Long id) {
        try {
            if (id == null || id < 0) {
                throw new IllegalArgumentException("Invalid id");
            }
            return merchantRepository.findById(id);
        } catch (Exception e) {
            log.error("Error while trying to find merchant by id: {}", id, e);
            throw e;
        }
    }

    public List<Merchant> getActiveMerchants() {
        try {
            TypedQuery<Merchant> query =
                entityManager.createQuery("SELECT m FROM Merchant m WHERE m.active = true", Merchant.class);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error while trying to get active merchants", e);
            throw e;
        }
    }

    @Transactional
    public Merchant getMerchantWithHighestTurnoverIn2022(int year) {
        try {
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
            Merchant merchant = query.getSingleResult();
            entityManager.close();
            return merchant;
        } catch (Exception e) {
            log.error("Error while trying to get merchant with highest turnover in {}", year, e);
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Transactional
    public boolean isMerchantWithHighestTurnoverActive(int year) {
        try {
            TypedQuery<Merchant> query = entityManager.createQuery(
                """
                    SELECT t.merchant FROM PaymentTransaction t WHERE YEAR(t.transactionDate) = :year GROUP BY t.merchant ORDER BY SUM(t.grossAmount) DESC
                    """
                , Merchant.class);
            query.setParameter("year", year);
            query.setMaxResults(1);
            Merchant merchant = query.getSingleResult();
            return merchant.isActive();
        } catch (Exception e) {
            log.error("Error while trying to check if merchant with highest turnover in {} is active", year, e);
            throw e;
        }
    }
}
