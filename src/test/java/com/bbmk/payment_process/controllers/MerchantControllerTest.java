package com.bbmk.payment_process.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import com.bbmk.payment_process.service.MerchantService;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class MerchantControllerTest {
    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    void testGetActiveMerchants() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        MerchantController merchantController = new MerchantController(new MerchantService(mock(MerchantRepository.class)));

        // Act
        ResponseEntity<List<Merchant>> actualActiveMerchants = merchantController.getActiveMerchants();

        // Assert
        assertNull(actualActiveMerchants.getBody());
        assertEquals(500, actualActiveMerchants.getStatusCodeValue());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    void testGetActiveMerchants2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // Arrange
        MerchantController merchantController = new MerchantController(null);

        // Act
        ResponseEntity<List<Merchant>> actualActiveMerchants = merchantController.getActiveMerchants();

        // Assert
        assertNull(actualActiveMerchants.getBody());
        assertEquals(500, actualActiveMerchants.getStatusCodeValue());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
    }
}

