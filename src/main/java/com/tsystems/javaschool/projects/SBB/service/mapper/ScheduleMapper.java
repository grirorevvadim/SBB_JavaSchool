package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;

@Mapper
public class ScheduleMapper {
    public ScheduleDTO mapToDto(Schedule schedule) {
        var dto = new ScheduleDTO();
        dto.setId(schedule.getId());
        dto.setTrainId(schedule.getTrain_id());
        dto.setArrivalDateTime(schedule.getArrivalDateTime());
        dto.setStation(schedule.getStation());
        return dto;
    }

    public Schedule mapToEntity(ScheduleDTO dto) {
        var schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setTrain_id(dto.getTrainId());
        schedule.setArrivalDateTime(dto.getArrivalDateTime());
        schedule.setStation(dto.getStation());
        return schedule;
    }
}
