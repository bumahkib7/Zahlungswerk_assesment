package com.bbmk.payment_process.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {MerchantService.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class MerchantServiceTest {
    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantService merchantService;

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById() {
        // Arrange
        Optional<Merchant> ofResult = Optional.of(new Merchant(1L, "Name", true));
        when(merchantRepository.findById((Long) any())).thenReturn(ofResult);
        long id = 1L;

        // Act
        Optional<Merchant> actualFindMerchantByIdResult = merchantService.findMerchantById(id);

        // Assert
        assertSame(ofResult, actualFindMerchantByIdResult);
        assertTrue(actualFindMerchantByIdResult.isPresent());
        verify(merchantRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById2() {
        // Arrange
        when(merchantRepository.findById((Long) any())).thenReturn(Optional.of(new Merchant(1L, "Name", true)));
        Long id = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(id));
    }
}

