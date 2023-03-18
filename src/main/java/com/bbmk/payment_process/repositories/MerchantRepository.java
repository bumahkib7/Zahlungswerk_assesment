package com.bbmk.payment_process.repositories;

import com.bbmk.payment_process.models.Merchant;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {


    @NotNull Optional<Merchant> findById(@NotNull Long id);

    @Query("SELECT m FROM Merchant m")
    @NotNull
    Page<Merchant> findAll(@NotNull Pageable pageable);

}
