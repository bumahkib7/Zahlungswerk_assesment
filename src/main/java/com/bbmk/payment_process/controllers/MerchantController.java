package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/merchants")
@Slf4j
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/{id}")
    public Optional<Merchant> getMerchantById(@PathVariable Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid id provided");
        }
        return merchantService.findMerchantById(id);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Merchant>> getActiveMerchants() {
        List<Merchant> activeMerchants = merchantService.getActiveMerchants();
        return activeMerchants.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(activeMerchants);
    }

    @GetMapping("/highest-turnover")
    public ResponseEntity<?> getMerchantWithHighestTurnoverInYear(@RequestParam("year") int year) {
        if (year < 1800 || year > 2099) {
            throw new IllegalArgumentException("Invalid year provided");
        }
        Merchant merchant = merchantService.getMerchantWithHighestTurnoverIn2022(year);
        return ResponseEntity.ok(merchant.getName());
    }

    @GetMapping("/highest-turnover-active")
    public ResponseEntity<?> isMerchantWithHighestTurnoverActiveInYear(@RequestParam("year") int year) {
        if (year < 1800 || year > 2099) {
            throw new IllegalArgumentException("Invalid year provided");
        }
        boolean isActive = merchantService.isMerchantWithHighestTurnoverActive(year);
        return ResponseEntity.ok(isActive);
    }
}
