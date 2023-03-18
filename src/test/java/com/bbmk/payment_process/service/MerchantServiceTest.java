package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.models.PaymentTransaction;
import com.bbmk.payment_process.repositories.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.assertj.core.util.Arrays;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.jdbc.internal.JdbcServicesImpl;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.internal.ProcedureCallImpl;
import org.hibernate.query.spi.QueryImplementor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
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
        // Arrange
        Optional<Merchant> ofResult = Optional.of(new Merchant(1L, "Name", true));
        when(merchantRepository.findById(any())).thenReturn(ofResult);
        long id = 1L;

        // Act
        Optional<Merchant> actualFindMerchantByIdResult = merchantService.findMerchantById(id);

        // Assert
        assertSame(ofResult, actualFindMerchantByIdResult);
        assertTrue(actualFindMerchantByIdResult.isPresent());
        verify(merchantRepository).findById(any());
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById2() {
        // Arrange
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant(1L, "Name", true)));
        Long id = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(id));
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById3() {
        // Arrange
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant(1L, "Name", true)));
        long id = -1L;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(id));
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    public void testFindMerchantById4() {
        // Arrange
        when(merchantRepository.findById(any())).thenThrow(new IllegalArgumentException());
        long id = 1L;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(id));
        verify(merchantRepository).findById(any());
    }



}

