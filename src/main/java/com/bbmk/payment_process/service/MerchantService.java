package com.example.zahlungswerk.service;

import com.example.zahlungswerk.models.Merchant;
import com.example.zahlungswerk.repositories.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Optional<Merchant> findMerchantById(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        return merchantRepository.findById(id);
    }

    public List<Merchant> findMerchantWithHighestTurnoverInYear2022(int year) {
        if (year == 0 || year < 0) {
            throw new IllegalArgumentException("Invalid year");
        }
        return merchantRepository.findMerchantWithHighestTurnoverInYear(year);
    }

    public List<Merchant> findActiveMerchants() {
        return merchantRepository.findByActive(true);
    }

    public boolean isMerchantwithHighestTurnOverstillActive(int year) {
        if (year == 0|| year < 0) {
            throw new IllegalArgumentException("Invalid year");
        }
        Optional<Merchant> merchantWithHighestTurnover = Optional.ofNullable(merchantRepository.findMerchantWithHighestTurnoverAndStillActive(year));
        return merchantWithHighestTurnover.isPresent() && merchantWithHighestTurnover.get().isActive();
    }


}
