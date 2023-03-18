package com.example.zahlungswerk.controllers;

import com.example.zahlungswerk.models.Merchant;
import com.example.zahlungswerk.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return merchantOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while getting merchant by id: {}", id, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Merchant>> getActiveMerchants() {
        try {
            List<Merchant> activeMerchants = merchantService.findActiveMerchants();
            return ResponseEntity.ok(activeMerchants);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error while getting active merchants", e);
            return ResponseEntity.status(500).build();
        }
    }
}
