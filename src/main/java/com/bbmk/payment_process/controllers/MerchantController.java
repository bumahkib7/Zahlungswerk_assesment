package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Optional<Merchant>> getMerchantById(@PathVariable Long id) {
        try {
            Optional<Optional<Merchant>> merchantOptional = Optional.ofNullable(merchantService.findMerchantById(id));
            return merchantOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            log.error("Error while getting merchant by id: {}", id, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while getting merchant by id: {}", id, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Merchant>> getActiveMerchants() {
        try {
            List<Merchant> activeMerchants = merchantService.getActiveMerchants();
            if (activeMerchants != null) {
                return ResponseEntity.ok(activeMerchants);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            log.error("Error while getting active merchants", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while getting active merchants", e);
            return ResponseEntity.status(500).build();
        } finally {
            log.info("Error and exceptions handling added");
        }
    }

    @GetMapping("/highest-turnover")
    public ResponseEntity<?> getMerchantWithHighestTurnoverInYear(@RequestParam("year") int year) {
        try {
            if (year < 1800 || year > 2099) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid year provided");
            }
            Merchant merchant = merchantService.getMerchantWithHighestTurnoverIn2022(year);
            return ResponseEntity.ok(merchant.getName());
        } catch (IllegalArgumentException e) {
            log.error("Error while getting merchant with highest turnover in year: {}", year, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while getting merchant with highest turnover in year: {}", year, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/highest-turnover-active")
    public ResponseEntity<?> isMerchantWithHighestTurnoverActiveInYear(@RequestParam("year") int year) {
        try {
            if (year < 1800 || year > 2099) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid year provided");
            }
            boolean isActive = merchantService.isMerchantWithHighestTurnoverActive(year);
            return ResponseEntity.ok(isActive);
        } catch (IllegalArgumentException e) {
            log.error("Error while checking if merchant with highest turnover is active in year: {}", year, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while checking if merchant with highest turnover is active in year: {}", year, e);
            return ResponseEntity.status(500).build();
        }
    }
}
