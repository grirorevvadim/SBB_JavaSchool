package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;

@Mapper
public class ScheduleMapper {
    public ScheduleDTO mapToDto(Schedule schedule) {
        var dto = new ScheduleDTO();
        dto.setScheduleId(schedule.getScheduleId());
        dto.setTrainId(schedule.getTrain());
        dto.setArrivalDateTime(schedule.getArrivalDateTime());
        return dto;
    }

    public Schedule mapToEntity(ScheduleDTO dto) {
        var schedule = new Schedule();
        schedule.setScheduleId(dto.getScheduleId());
        schedule.setTrain(dto.getTrainId());
        schedule.setArrivalDateTime(dto.getArrivalDateTime());
        return schedule;
    }
}
