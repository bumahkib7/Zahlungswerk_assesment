package com.example.zahlungswerk.repositories;

import com.example.zahlungswerk.models.Merchant;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @NotNull
    List<Merchant> findAll();

    @NotNull Optional<Merchant> findById(@NotNull Long id);

    @Query("select m from Merchant m where m.active = true ")
    List<Merchant> findByActive(@Param("active") boolean active);

    List<Merchant findMerchantByHighestTurnover(@Param("year") int year);


    @Query("SELECT m FROM Merchant m JOIN  t WHERE m.Active = true GROUP BY m.id ORDER BY COUNT(t) DESC")
    List<Merchant> findMerchantByActiveAndTransactionsIsHighest();
    // Pagination query
    @Query("SELECT m FROM Merchant m")
    @NotNull
    Page<Merchant> findAll(@NotNull Pageable pageable);

}
