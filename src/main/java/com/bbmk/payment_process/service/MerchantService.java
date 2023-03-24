package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Optional<Merchant> findMerchantById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        return merchantRepository.findById(id);
    }


    public List<Merchant> getActiveMerchants(boolean isActive) {
        TypedQuery<Merchant> query =
            entityManager.createQuery("SELECT m FROM Merchant m WHERE m.active = :isActive ", Merchant.class);
        query.setParameter("isActive", isActive);
        return query.getResultList();
    }


    @Transactional
    public List<Merchant> getMerchantsWithHighestTurnover(int year, boolean isActive) {
        try {
            String sql = """
                WITH merchant_gross_amounts AS (
                SELECT pt.merchant_id, SUM(pt.gross_amount) as total_gross_amount
                FROM payment_transaction pt
                WHERE EXTRACT(YEAR FROM pt.transaction_date) = :year
                GROUP BY pt.merchant_id
                ),
                max_gross_amount AS (
                SELECT MAX(total_gross_amount) as max_amount
                FROM merchant_gross_amounts
                )
                SELECT m.merchant_id AS id, m.name AS name, m.active AS active
                FROM merchant m
                JOIN merchant_gross_amounts mga ON m.merchant_id = mga.merchant_id
                JOIN max_gross_amount mga_max ON mga.total_gross_amount = mga_max.max_amount
                WHERE m.active = :isActive;
                """;
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("year", year);
            params.addValue("isActive", isActive);
            return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Merchant.class));
        } catch (Exception e) {
            log.error("Error while getting merchants with highest turnover: ", e);
            throw e;
        }
    }


    public Page<Merchant> findAllWithLimit(int pageNumber, int limit, Sort.Direction direction, String sortProperty) {
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, Sort.by(direction, sortProperty));
        return merchantRepository.findAll(pageable);
    }


}
