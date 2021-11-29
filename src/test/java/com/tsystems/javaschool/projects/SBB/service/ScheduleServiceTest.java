package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.RootRepository;
import com.tsystems.javaschool.projects.SBB.repository.ScheduleRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.RootMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {
    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ScheduleMapper scheduleMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getScheduleByScheduleId() {
        Schedule schedule = new Schedule();
        schedule.setId(123L);
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(123L);
        when(scheduleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(schedule));
        when(scheduleMapper.mapToDto(schedule)).thenReturn(dto);

        scheduleService.getScheduleByScheduleId(1234L);

        verify(scheduleRepository, times(1)).findById(anyLong());
        verify(scheduleMapper, times(1)).mapToDto(any(Schedule.class));
    }

    @Test
    void getScheduleByNull() {
        when(scheduleRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> scheduleService.getScheduleByScheduleId(1234L));
    }
}