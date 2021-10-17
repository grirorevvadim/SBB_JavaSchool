package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.repository.ScheduleRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final Utils utils;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        var schedule = scheduleMapper.mapToEntity(scheduleDTO);
        //schedule.setArrivalDateTime();
        return null;
    }

    private LocalDateTime getArrivalDateTime(Schedule schedule) {
        return null;
    }
}
