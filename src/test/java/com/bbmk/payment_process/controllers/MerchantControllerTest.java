package com.bbmk.payment_process.controllers;

import com.bbmk.payment_process.models.Merchant;
import com.bbmk.payment_process.repositories.MerchantRepository;
import com.bbmk.payment_process.service.MerchantService;
import org.hibernate.engine.jdbc.internal.JdbcServicesImpl;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.engine.spi.SessionFactoryDelegatingImpl;
import org.hibernate.procedure.internal.ProcedureCallImpl;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MerchantControllerTest {
    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    public void testGetMerchantById() {

        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        assertThrows(IllegalArgumentException.class,
            () -> (new MerchantController(
                new MerchantService(merchantRepository, new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null)))))
                .getMerchantById(null));
    }

    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    public void testGetMerchantById2() {

        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        Optional<Merchant> ofResult = Optional.of(new Merchant("Name", true));
        when(merchantRepository.findById(any())).thenReturn(ofResult);
        Optional<Merchant> actualMerchantById = (new MerchantController(new MerchantService(merchantRepository,
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))))))
            .getMerchantById(1L);
        assertSame(ofResult, actualMerchantById);
        assertTrue(actualMerchantById.isPresent());
        verify(merchantRepository).findById(any());
    }


    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    public void testGetMerchantById4() {

        MerchantService merchantService = mock(MerchantService.class);
        Optional<Merchant> ofResult = Optional.of(new Merchant("Name", true));
        when(merchantService.findMerchantById(any())).thenReturn(ofResult);
        Optional<Merchant> actualMerchantById = (new MerchantController(merchantService)).getMerchantById(1L);
        assertSame(ofResult, actualMerchantById);
        assertTrue(actualMerchantById.isPresent());
        verify(merchantService).findMerchantById(any());
    }

    /**
     * Method under test: {@link MerchantController#getMerchantById(Long)}
     */
    @Test
    public void testGetMerchantById5() {

        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.findMerchantById(any())).thenReturn(Optional.of(new Merchant("Name", true)));
        assertThrows(IllegalArgumentException.class,
            () -> (new MerchantController(merchantService)).getMerchantById(-1L));
    }


    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    public void testGetActiveMerchants3() {

        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getJdbcServices()).thenReturn(new JdbcServicesImpl());
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen();
        doNothing().when(sessionDelegatorBaseImpl).markForRollbackOnly();
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl))));
        ProcedureCallImpl<Merchant> procedureCallImpl = (ProcedureCallImpl<Merchant>) mock(ProcedureCallImpl.class);
        when(procedureCallImpl.getResultList()).thenReturn(new ArrayList<>());
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.createQuery(any(), (Class<Merchant>) any()))
            .thenReturn(procedureCallImpl);
        ResponseEntity<List<Merchant>> actualActiveMerchants = (new MerchantController(
            new MerchantService(mock(MerchantRepository.class),
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1)))))
            .getActiveMerchants();
        assertNull(actualActiveMerchants.getBody());
        assertEquals(404, actualActiveMerchants.getStatusCodeValue());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
        verify(sessionDelegatorBaseImpl1).createQuery(any(), (Class<Merchant>) any());
        verify(procedureCallImpl).getResultList();
    }

    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    public void testGetActiveMerchants4() {

        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getJdbcServices()).thenReturn(new JdbcServicesImpl());
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen();
        doNothing().when(sessionDelegatorBaseImpl).markForRollbackOnly();
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl))));

        ArrayList<Merchant> merchantList = new ArrayList<>();
        merchantList.add(new Merchant("SELECT m FROM Merchant m WHERE m.active = true", true));
        ProcedureCallImpl<Merchant> procedureCallImpl = (ProcedureCallImpl<Merchant>) mock(ProcedureCallImpl.class);
        when(procedureCallImpl.getResultList()).thenReturn(merchantList);
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.createQuery(any(), (Class<Merchant>) any()))
            .thenReturn(procedureCallImpl);
        ResponseEntity<List<Merchant>> actualActiveMerchants = (new MerchantController(
            new MerchantService(mock(MerchantRepository.class),
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1)))))
            .getActiveMerchants();
        assertTrue(actualActiveMerchants.hasBody());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
        assertEquals(200, actualActiveMerchants.getStatusCodeValue());
        verify(sessionDelegatorBaseImpl1).createQuery(any(), (Class<Merchant>) any());
        verify(procedureCallImpl).getResultList();
    }

    /**
     * Method under test: {@link MerchantController#getActiveMerchants()}
     */
    @Test
    public void testGetActiveMerchants5() {

        SessionDelegatorBaseImpl sessionDelegatorBaseImpl = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl.getJdbcServices()).thenReturn(new JdbcServicesImpl());
        when(sessionDelegatorBaseImpl.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        doNothing().when(sessionDelegatorBaseImpl).checkOpen();
        doNothing().when(sessionDelegatorBaseImpl).markForRollbackOnly();
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
            new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl))));
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl1 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl1.getFactory()).thenReturn(
            new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(
                new SessionFactoryDelegatingImpl(new SessionFactoryDelegatingImpl(null))))));
        SessionDelegatorBaseImpl sessionDelegatorBaseImpl2 = mock(SessionDelegatorBaseImpl.class);
        when(sessionDelegatorBaseImpl2.createQuery(any(), any()))
            .thenReturn(new ProcedureCallImpl<>(
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(
                    new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl1)))),
                "Procedure Name"));
        when(sessionDelegatorBaseImpl2.createQuery(any(), (Class<Merchant>) any()))
            .thenReturn((ProcedureCallImpl<Merchant>) mock(ProcedureCallImpl.class));
        new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(sessionDelegatorBaseImpl2));
        MerchantService merchantService = mock(MerchantService.class);
        when(merchantService.getActiveMerchants()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Merchant>> actualActiveMerchants = (new MerchantController(merchantService))
            .getActiveMerchants();
        assertNull(actualActiveMerchants.getBody());
        assertEquals(404, actualActiveMerchants.getStatusCodeValue());
        assertTrue(actualActiveMerchants.getHeaders().isEmpty());
        verify(sessionDelegatorBaseImpl2).createQuery(any(), any());
        verify(sessionDelegatorBaseImpl1).getFactory();
        verify(merchantService).getActiveMerchants();
    }

    /**
     * Method under test: {@link MerchantController#getMerchantWithHighestTurnoverInYear(int)}
     */
    @Test
    public void testGetMerchantWithHighestTurnoverInYear() {

        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        assertThrows(IllegalArgumentException.class,
            () -> (new MerchantController(new MerchantService(merchantRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))))))
                .getMerchantWithHighestTurnoverInYear(1));
    }


    /**
     * Method under test: {@link MerchantController#isMerchantWithHighestTurnoverActiveInYear(int)}
     */
    @Test
    public void testIsMerchantWithHighestTurnoverActiveInYear() {

        MerchantRepository merchantRepository = mock(MerchantRepository.class);
        assertThrows(IllegalArgumentException.class,
            () -> (new MerchantController(new MerchantService(merchantRepository,
                new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(new SessionDelegatorBaseImpl(null))))))
                .isMerchantWithHighestTurnoverActiveInYear(1));
    }


}

