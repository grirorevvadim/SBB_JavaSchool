package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Mapper
@RequiredArgsConstructor
public class ScheduleMapper {
    private final StationMapper stationMapper;


    public ScheduleDTO mapToDto(Schedule schedule) {
        var dto = new ScheduleDTO();
        var train = new TrainDTO();
        train.setAllSeatsNumber(schedule.getTrain_id().getAllSeatsNumber());
        train.setAvailableSeatsNumber(schedule.getTrain_id().getAvailableSeatsNumber());
        train.setTrainType(schedule.getTrain_id().getTrainType());
        train.setTrainNumber(schedule.getTrain_id().getTrainNumber());
        train.setId(schedule.getTrain_id().getId());
        dto.setId(schedule.getId());
        dto.setTrainId(train);
        dto.setArrivalDateTime(schedule.getArrivalDateTime());
        dto.setStation(stationMapper.mapToDto(schedule.getStation()));
        return dto;
    }

    public Schedule mapToEntity(ScheduleDTO dto) {
        var schedule = new Schedule();
        schedule.setId(dto.getId());
        Train train = new Train();
        train.setId(dto.getTrainId().getId());
        train.setTrainNumber(dto.getTrainId().getTrainNumber());
        train.setAvailableSeatsNumber(dto.getTrainId().getAvailableSeatsNumber());
        train.setAllSeatsNumber(dto.getTrainId().getAllSeatsNumber());
        schedule.setTrain_id(train);
        schedule.setArrivalDateTime(dto.getArrivalDateTime());
        schedule.setStation(stationMapper.mapToEntity(dto.getStation()));
        return schedule;
    }
}
