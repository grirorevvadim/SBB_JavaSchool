package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StationServiceTest {
    @InjectMocks
    StationService stationService;

    @Mock
    StationRepository stationRepository;

    @Mock
    StationMapper stationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findStationByStationId() {
        Station station = new Station();
        station.setId(123L);
        station.setStationName("Test");
        StationDTO stationDTO = new StationDTO();
        stationDTO.setId(123L);

        when(stationRepository.findById(anyLong())).thenReturn(java.util.Optional.of(station));
        when(stationMapper.mapToDto(any(Station.class))).thenReturn(stationDTO);

        stationService.findStationByStationId(1234L);
        verify(stationRepository, times(1)).findById(anyLong());
        verify(stationMapper, times(1)).mapToDto(any(Station.class));
    }

    @Test
    void findStationByNull() {
        when(stationRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> stationService.findStationByStationId(1234L));
    }

    @Test
    void getStationByStationName() {
        Station station = new Station();
        station.setId(123L);
        station.setStationName("Test");
        StationDTO stationDTO = new StationDTO();
        stationDTO.setId(123L);

        when(stationRepository.findByStationName(anyString())).thenReturn(station);
        when(stationMapper.mapToDto(any(Station.class))).thenReturn(stationDTO);

        stationService.getStationByStationName("StationA");
        verify(stationRepository, times(1)).findByStationName(anyString());
        verify(stationMapper, times(1)).mapToDto(any(Station.class));
    }

    @Test
    void findStationByNullStationName() {
        when(stationRepository.findByStationName(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> stationService.getStationByStationName(anyString()));
    }
}