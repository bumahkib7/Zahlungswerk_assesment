package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        Optional<Merchant> ofResult = Optional.of(new Merchant("Name", true));
        when(merchantRepository.findById(any())).thenReturn(ofResult);
        Optional<Merchant> actualFindMerchantByIdResult = merchantService.findMerchantById(1L);
        assertSame(ofResult, actualFindMerchantByIdResult);
        assertTrue(actualFindMerchantByIdResult.isPresent());
        verify(merchantRepository).findById(any());
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById2() {
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant("Name", true)));
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(null));
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById3() {
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant("Name", true)));
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(-1L));
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById4() {
        when(merchantRepository.findById(any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(1L));
        verify(merchantRepository).findById(any());
    }

}

