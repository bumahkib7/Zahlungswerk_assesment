package com.bbmk.payment_process.service;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MerchantService.class})
@ExtendWith(SpringExtension.class)
class MerchantServiceTest {
    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantService merchantService;

    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    void testFindMerchantById() {
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
    void testFindMerchantById2() {
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant("Name", true)));
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(null));
    }

    /**
     * Method under test: {@link MerchantService#findMerchantById(Long)}
     */
    @Test
    void testFindMerchantById3() {
        when(merchantRepository.findById(any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> merchantService.findMerchantById(1L));
        verify(merchantRepository).findById(any());
    }


    /**
     * Method under test: {@link MerchantService#getMerchantsWithHighestTurnover(int, boolean)}
     */
    @Test
    void testGetMerchantsWithHighestTurnover() throws DataAccessException {
        when(namedParameterJdbcTemplate.query(anyString(), any(SqlParameterSource.class), any(RowMapper.class)))
            .thenReturn(new ArrayList<>());
        assertTrue(merchantService.getMerchantsWithHighestTurnover(1, true).isEmpty());
        verify(namedParameterJdbcTemplate).query(anyString(), any(SqlParameterSource.class), any(RowMapper.class));
    }


    /**
     * Method under test: {@link MerchantService#getMerchantsWithHighestTurnover(int, boolean)}
     */
    @Test
    void testGetMerchantsWithHighestTurnover2() throws DataAccessException {
        ArrayList<Merchant> merchantList = new ArrayList<>();
        merchantList.add(new Merchant()); // Add a Merchant instance
        when(namedParameterJdbcTemplate.query(anyString(), any(SqlParameterSource.class), any(RowMapper.class)))
            .thenReturn(merchantList);
        List<Merchant> actualMerchantsWithHighestTurnover = merchantService.getMerchantsWithHighestTurnover(1, true);
        assertSame(merchantList, actualMerchantsWithHighestTurnover);
        assertFalse(actualMerchantsWithHighestTurnover.isEmpty());
        verify(namedParameterJdbcTemplate).query(anyString(), any(SqlParameterSource.class), any(RowMapper.class));
    }

    /**
     * Method under test: {@link MerchantService#getMerchantsWithHighestTurnover(int, boolean)}
     */
    @Test
    void testGetMerchantsWithHighestTurnover3() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(namedParameterJdbcTemplate.query(any(), (SqlParameterSource) any(), (RowMapper<Object>) any()))
            .thenReturn(objectList);
        List<Merchant> actualMerchantsWithHighestTurnover = merchantService.getMerchantsWithHighestTurnover(1, true);
        assertSame(objectList, actualMerchantsWithHighestTurnover);
        assertTrue(actualMerchantsWithHighestTurnover.isEmpty());
        verify(namedParameterJdbcTemplate).query(any(), (SqlParameterSource) any(), (RowMapper<Object>) any());
    }


}

