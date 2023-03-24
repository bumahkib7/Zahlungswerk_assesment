package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.mixin.MerchantDTOMixin;
import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.service.MerchantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/merchants")
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/{id}")
    public ResponseEntity<Merchant> getMerchantById(@PathVariable Long id) {
        if (id == null || id < 0) {
            log.error("Invalid id provided" + id);
            throw new IllegalArgumentException("Invalid id provided");
        }
        return merchantService.findMerchantById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Merchant>> findAllWithLimit(
        @RequestParam(value = "page", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(value = "limit", defaultValue = "30", required = false) int limit) {

        Page<Merchant> merchants = merchantService.findAllWithLimit(pageNumber, limit, Sort.Direction.ASC, "name");
        return ResponseEntity.ok(merchants);
    }

    @GetMapping("/is-active")
    public ResponseEntity<List<Merchant>> getIsActiveMerchants(@RequestParam("active") boolean isActive) {
        List<Merchant> activeMerchants = merchantService.getActiveMerchants(isActive);
        return activeMerchants.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(activeMerchants);
    }

    @GetMapping("/highest-turnover")
    public ResponseEntity<String> getMerchantWithHighestTurnoverInYear(@RequestParam("year") int year,
                                                                       @RequestParam(value = "is_active", required = false)
                                                                       boolean isActive
    ) {
        if (year < 1800 || year > 2099) {
            log.error("invalid year" + year);
            throw new IllegalArgumentException("Invalid year provided");
        }
        List<Merchant> merchants = merchantService.getMerchantsWithHighestTurnover(year, isActive);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);

            objectMapper.addMixIn(Merchant.class, MerchantDTOMixin.class);
            String merchantsJson = objectMapper.writeValueAsString(merchants);

            return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(merchantsJson);
        } catch (Exception e) {
            log.error("Failed to create merchants " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error converting merchants to JSON: " + e.getMessage());

        }
    }


}
