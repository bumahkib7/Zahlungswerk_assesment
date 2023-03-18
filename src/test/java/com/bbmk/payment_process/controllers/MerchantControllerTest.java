package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import com.bbmk.payment_process.service.MerchantService;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.internal.JdbcServicesImpl;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.procedure.internal.ProcedureCallImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MerchantControllerTest {
    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    void testGetMerchantById() {

        // Arrange
        MerchantController merchantController = new MerchantController(new MerchantService(mock(MerchantRepository.class)));
        Long id = null;

        // Act
        ResponseEntity<Optional<Merchant>> actualMerchantById = merchantController.getMerchantById(id);

        // Assert
        assertNull(actualMerchantById.getBody());
        assertEquals(400, actualMerchantById.getStatusCodeValue());
        assertTrue(actualMerchantById.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    void testGetMerchantById2() {

        // Arrange
        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        when(merchantRepository.findById(any())).thenReturn(Optional.of(new Merchant("Name", true)));
        MerchantController merchantController = new MerchantController(new MerchantService(merchantRepository));
        long id = 1L;

        // Act
        ResponseEntity<Optional<Merchant>> actualMerchantById = merchantController.getMerchantById(id);

        // Assert
        assertTrue(actualMerchantById.getBody().isPresent());
        assertTrue(actualMerchantById.getHeaders().isEmpty());
        assertEquals(200, actualMerchantById.getStatusCodeValue());
        verify(merchantRepository).findById(any());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    void testGetMerchantById3() {

        // Arrange
        MerchantController merchantController = new MerchantController(null);
        long id = 1L;

        // Act
        ResponseEntity<Optional<Merchant>> actualMerchantById = merchantController.getMerchantById(id);

        // Assert
        assertNull(actualMerchantById.getBody());
        assertEquals(500, actualMerchantById.getStatusCodeValue());
        assertTrue(actualMerchantById.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    void testGetMerchantById4() {

        // Arrange
        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        when(merchantRepository.findById(any())).thenThrow(new IllegalArgumentException());
        MerchantController merchantController = new MerchantController(new MerchantService(merchantRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        long id = 1L;

        // Act
        ResponseEntity<Optional<Merchant>> actualMerchantById = merchantController.getMerchantById(id);

        // Assert
        assertNull(actualMerchantById.getBody());
        assertEquals(400, actualMerchantById.getStatusCodeValue());
        assertTrue(actualMerchantById.getHeaders().isEmpty());
        verify(merchantRepository).findById(any());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    void testGetMerchantById5() {

        // Arrange
        new IllegalArgumentException();
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.findMerchantById(any())).thenReturn(Optional.of(new Merchant("Name", true)));
        MerchantController merchantController = new MerchantController(merchantService);
        long id = 1L;

        // Act
        ResponseEntity<Optional<Merchant>> actualMerchantById = merchantController.getMerchantById(id);

        // Assert
        assertTrue(actualMerchantById.getBody().isPresent());
        assertTrue(actualMerchantById.getHeaders().isEmpty());
        assertEquals(200, actualMerchantById.getStatusCodeValue());
        verify(merchantService).findMerchantById(any());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    void testGetMerchantById6() {

        // Arrange
        new IllegalArgumentException();
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.findMerchantById(any())).thenThrow(new IllegalArgumentException());
        MerchantController merchantController = new MerchantController(merchantService);
        long id = 1L;

        // Act
        ResponseEntity<Optional<Merchant>> actualMerchantById = merchantController.getMerchantById(id);

        // Assert
        assertNull(actualMerchantById.getBody());
        assertEquals(400, actualMerchantById.getStatusCodeValue());
        assertTrue(actualMerchantById.getHeaders().isEmpty());
        verify(merchantService).findMerchantById(any());
    }

    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    void testGetActiveMerchants() {

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

        // Arrange
        MerchantController merchantController = new MerchantController(null);

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
    void testGetActiveMerchants3() {

        // Arrange
        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        MerchantController merchantController = new MerchantController(new MerchantService(merchantRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));

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
    void testGetActiveMerchants4() {

        // Arrange
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.getActiveMerchants()).thenReturn(new ArrayList<>());
        MerchantController merchantController = new MerchantController(merchantService);

        // Act
        ResponseEntity<List<Merchant>> actualActiveMerchants = merchantController.getActiveMerchants();

        // Assert
        assertTrue(actualActiveMerchants.hasBody());
        assertEquals(200, actualActiveMerchants.getStatusCodeValue());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
        verify(merchantService).getActiveMerchants();
    }

    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    void testGetActiveMerchants5() {

        // Arrange
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.getActiveMerchants()).thenThrow(new IllegalArgumentException());
        MerchantController merchantController = new MerchantController(merchantService);

        // Act
        ResponseEntity<List<Merchant>> actualActiveMerchants = merchantController.getActiveMerchants();

        // Assert
        assertNull(actualActiveMerchants.getBody());
        assertEquals(400, actualActiveMerchants.getStatusCodeValue());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
        verify(merchantService).getActiveMerchants();
    }


    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    void testGetActiveMerchants8() {

        // Arrange
        new IllegalArgumentException();
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getJdbcServices()).thenReturn(mock(JdbcServicesImpl.class));
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen();
        doNothing().when(sessionDelegatorBaseImpl).markForRollbackOnly();
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl))));
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.createQuery(any(), (Class<Merchant>) any())).thenReturn(null);
        MerchantController merchantController = new MerchantController(new MerchantService(mock(MerchantRepository.class),
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1))));

        // Act
        ResponseEntity<List<Merchant>> actualActiveMerchants = merchantController.getActiveMerchants();

        // Assert
        assertNull(actualActiveMerchants.getBody());
        assertEquals(500, actualActiveMerchants.getStatusCodeValue());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
        verify(sessionDelegatorBaseImpl1).createQuery(any(), (Class<Merchant>) any());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear() {

        // Arrange
        MerchantController merchantController = new MerchantController(
            new MerchantService(mock(MerchantRepository.class)));
        int year = 1;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertEquals("Invalid year provided", actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(400, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear2() {

        // Arrange
        MerchantController merchantController = new MerchantController(
            new MerchantService(mock(MerchantRepository.class)));
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertNull(actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(500, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear3() {

        // Arrange
        MerchantController merchantController = new MerchantController(null);
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertNull(actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(500, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear4() {

        // Arrange
        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        MerchantController merchantController = new MerchantController(new MerchantService(merchantRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertNull(actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(500, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear5() {

        // Arrange
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.getMerchantWithHighestTurnoverIn2022(anyInt())).thenReturn(new Merchant("Name", true));
        MerchantController merchantController = new MerchantController(merchantService);
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertEquals("Name", actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(200, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
        verify(merchantService).getMerchantWithHighestTurnoverIn2022(anyInt());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear6() {

        // Arrange
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.getMerchantWithHighestTurnoverIn2022(anyInt())).thenReturn(null);
        MerchantController merchantController = new MerchantController(merchantService);
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertNull(actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(500, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
        verify(merchantService).getMerchantWithHighestTurnoverIn2022(anyInt());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear7() {

        // Arrange
        Merchant merchant = mock(Merchant.class);
        when(merchant.getName()).thenThrow(new IllegalArgumentException());
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.getMerchantWithHighestTurnoverIn2022(anyInt())).thenReturn(merchant);
        MerchantController merchantController = new MerchantController(merchantService);
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertNull(actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(400, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
        verify(merchantService).getMerchantWithHighestTurnoverIn2022(anyInt());
        verify(merchant).getName();
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear8() {

        // Arrange
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.getMerchantWithHighestTurnoverIn2022(anyInt())).thenThrow(new IllegalArgumentException());
        MerchantController merchantController = new MerchantController(merchantService);
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertNull(actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(400, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
        verify(merchantService).getMerchantWithHighestTurnoverIn2022(anyInt());
    }


    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    void testGetMerchantWithHighestTurnoverInYear10() throws HibernateException {

        // Arrange
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen(anyBoolean());
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl))));
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.createQuery(any(), (Class<Merchant>) any())).thenReturn(null);
        doNothing().when(sessionDelegatorBaseImpl1).close();
        MerchantController merchantController = new MerchantController(new MerchantService(mock(MerchantRepository.class),
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1))));
        int year = 1800;

        // Act
        ResponseEntity<?> actualMerchantWithHighestTurnoverInYear = merchantController
            .getMerchantWithHighestTurnoverInYear(year);

        // Assert
        assertNull(actualMerchantWithHighestTurnoverInYear.getBody());
        assertEquals(500, actualMerchantWithHighestTurnoverInYear.getStatusCodeValue());
        assertTrue(actualMerchantWithHighestTurnoverInYear.getHeaders().isEmpty());
        verify(sessionDelegatorBaseImpl1).createQuery(any(), (Class<Merchant>) any());
        verify(sessionDelegatorBaseImpl1).close();
    }

    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    void testIsMerchantWithHighestTurnoverActiveInYear() {

        // Arrange
        MerchantController merchantController = new MerchantController(
            new MerchantService(mock(MerchantRepository.class)));
        int year = 1;

        // Act
        ResponseEntity<?> actualIsMerchantWithHighestTurnoverActiveInYearResult = merchantController
            .isMerchantWithHighestTurnoverActiveInYear(year);

        // Assert
        assertEquals("Invalid year provided", actualIsMerchantWithHighestTurnoverActiveInYearResult.getBody());
        assertEquals(400, actualIsMerchantWithHighestTurnoverActiveInYearResult.getStatusCodeValue());
        assertTrue(actualIsMerchantWithHighestTurnoverActiveInYearResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    void testIsMerchantWithHighestTurnoverActiveInYear2() {

        // Arrange
        MerchantController merchantController = new MerchantController(
            new MerchantService(mock(MerchantRepository.class)));
        int year = 1800;

        // Act
        ResponseEntity<?> actualIsMerchantWithHighestTurnoverActiveInYearResult = merchantController
            .isMerchantWithHighestTurnoverActiveInYear(year);

        // Assert
        assertNull(actualIsMerchantWithHighestTurnoverActiveInYearResult.getBody());
        assertEquals(500, actualIsMerchantWithHighestTurnoverActiveInYearResult.getStatusCodeValue());
        assertTrue(actualIsMerchantWithHighestTurnoverActiveInYearResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    void testIsMerchantWithHighestTurnoverActiveInYear3() {

        // Arrange
        MerchantController merchantController = new MerchantController(null);
        int year = 1800;

        // Act
        ResponseEntity<?> actualIsMerchantWithHighestTurnoverActiveInYearResult = merchantController
            .isMerchantWithHighestTurnoverActiveInYear(year);

        // Assert
        assertNull(actualIsMerchantWithHighestTurnoverActiveInYearResult.getBody());
        assertEquals(500, actualIsMerchantWithHighestTurnoverActiveInYearResult.getStatusCodeValue());
        assertTrue(actualIsMerchantWithHighestTurnoverActiveInYearResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    void testIsMerchantWithHighestTurnoverActiveInYear4() {

        // Arrange
        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        MerchantController merchantController = new MerchantController(new MerchantService(merchantRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))));
        int year = 1800;

        // Act
        ResponseEntity<?> actualIsMerchantWithHighestTurnoverActiveInYearResult = merchantController
            .isMerchantWithHighestTurnoverActiveInYear(year);

        // Assert
        assertNull(actualIsMerchantWithHighestTurnoverActiveInYearResult.getBody());
        assertEquals(500, actualIsMerchantWithHighestTurnoverActiveInYearResult.getStatusCodeValue());
        assertTrue(actualIsMerchantWithHighestTurnoverActiveInYearResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    void testIsMerchantWithHighestTurnoverActiveInYear5() {

        // Arrange
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.isMerchantWithHighestTurnoverActive(anyInt())).thenReturn(true);
        MerchantController merchantController = new MerchantController(merchantService);
        int year = 1800;

        // Act
        ResponseEntity<?> actualIsMerchantWithHighestTurnoverActiveInYearResult = merchantController
            .isMerchantWithHighestTurnoverActiveInYear(year);

        // Assert
        Object expectedHasBodyResult = actualIsMerchantWithHighestTurnoverActiveInYearResult.getBody();
        assertSame(expectedHasBodyResult, actualIsMerchantWithHighestTurnoverActiveInYearResult.hasBody());
        assertEquals(200, actualIsMerchantWithHighestTurnoverActiveInYearResult.getStatusCodeValue());
        assertTrue(actualIsMerchantWithHighestTurnoverActiveInYearResult.getHeaders().isEmpty());
        verify(merchantService).isMerchantWithHighestTurnoverActive(anyInt());
    }

    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    void testIsMerchantWithHighestTurnoverActiveInYear6() {

        // Arrange
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.isMerchantWithHighestTurnoverActive(anyInt())).thenThrow(new IllegalArgumentException());
        MerchantController merchantController = new MerchantController(merchantService);
        int year = 1800;

        // Act
        ResponseEntity<?> actualIsMerchantWithHighestTurnoverActiveInYearResult = merchantController
            .isMerchantWithHighestTurnoverActiveInYear(year);

        // Assert
        assertNull(actualIsMerchantWithHighestTurnoverActiveInYearResult.getBody());
        assertEquals(400, actualIsMerchantWithHighestTurnoverActiveInYearResult.getStatusCodeValue());
        assertTrue(actualIsMerchantWithHighestTurnoverActiveInYearResult.getHeaders().isEmpty());
        verify(merchantService).isMerchantWithHighestTurnoverActive(anyInt());
    }



    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    void testIsMerchantWithHighestTurnoverActiveInYear8() {

        // Arrange
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen(anyBoolean());
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl))));
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.createQuery(any(), (Class<Merchant>) any())).thenReturn(null);
        MerchantController merchantController = new MerchantController(new MerchantService(mock(MerchantRepository.class),
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1))));
        int year = 1800;

        // Act
        ResponseEntity<?> actualIsMerchantWithHighestTurnoverActiveInYearResult = merchantController
            .isMerchantWithHighestTurnoverActiveInYear(year);

        // Assert
        assertNull(actualIsMerchantWithHighestTurnoverActiveInYearResult.getBody());
        assertEquals(500, actualIsMerchantWithHighestTurnoverActiveInYearResult.getStatusCodeValue());
        assertTrue(actualIsMerchantWithHighestTurnoverActiveInYearResult.getHeaders().isEmpty());
        verify(sessionDelegatorBaseImpl1).createQuery(any(), (Class<Merchant>) any());
    }
}

