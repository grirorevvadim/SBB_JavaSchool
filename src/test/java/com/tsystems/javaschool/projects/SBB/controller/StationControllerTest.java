package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class StationControllerTest {
    @InjectMocks
    StationController stationController;

    @Mock
    StationService stationService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStation() {
        StationDTO stationDTO = new StationDTO();
        stationDTO.setStationName("StationA");
        stationDTO.setId(123L);
        when(stationService.findStationByStationId(anyLong())).thenReturn(stationDTO);

        StationDTO result = stationController.getStation(12342L);
        assertEquals(result.getStationName(), stationDTO.getStationName());
        assertEquals(result.getId(), stationDTO.getId());
    }
}