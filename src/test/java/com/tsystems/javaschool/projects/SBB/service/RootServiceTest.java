package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.RootRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.RootMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RootServiceTest {
    @InjectMocks
    RootService rootService;

    @Mock
    RootRepository rootRepository;

    @Mock
    RootMapper rootMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRootByRootId() {
        Station a = new Station();
        a.setStationName("stationA");
        Station b = new Station();
        b.setStationName("stationB");
        StationDTO a1 = new StationDTO();
        a1.setId(1L);
        a1.setStationName("stationA");
        StationDTO a2 = new StationDTO();
        a2.setId(2L);
        a2.setStationName("stationB");
        Root root = new Root();
        RootDTO dto = new RootDTO();
        root.setId(4321L);
        root.setStationsList(Arrays.asList(a, b));
        dto.setId(4321L);
        dto.setStationsList(Arrays.asList(a1, a2));
        when(rootRepository.findById(anyLong())).thenReturn(java.util.Optional.of(root));
        when(rootMapper.mapToDto(root)).thenReturn(dto);

        rootService.getRootByRootId(12345L);

        verify(rootRepository, times(1)).findById(anyLong());
        verify(rootMapper, times(1)).mapToDto(any(Root.class));
    }

    @Test
    final void getRootByNull() {
        when(rootRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> rootService.getRootByRootId(12345L));
    }
}