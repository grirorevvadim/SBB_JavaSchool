package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class ScheduleMapper {
    private final StationMapper stationMapper;


    public ScheduleDTO mapToDto(Schedule schedule) {
        var dto = new ScheduleDTO();
        if (schedule.getTrain() != null) {
            var train = new TrainDTO();
            train.setAllSeatsNumber(schedule.getTrain().getAllSeatsNumber());
            train.setTrainType(schedule.getTrain().getTrainType());
            train.setTrainNumber(schedule.getTrain().getTrainNumber());
            train.setId(schedule.getTrain().getId());
            dto.setTrainId(train);
        }
        dto.setAvailableSeatsNumber(schedule.getAvailableSeatsNumber());
        dto.setId(schedule.getId());
        dto.setArrivalDateTime(schedule.getArrivalDateTime());
        dto.setStation(stationMapper.mapToDto(schedule.getStation()));
        return dto;
    }

    public Schedule mapToEntity(ScheduleDTO dto) {
        var schedule = new Schedule();
        schedule.setId(dto.getId());
        if (dto.getTrainId() != null) {
            Train train = new Train();
            train.setId(dto.getTrainId().getId());
            train.setTrainNumber(dto.getTrainId().getTrainNumber());
            train.setAllSeatsNumber(dto.getTrainId().getAllSeatsNumber());
            schedule.setTrain(train);
        }
        schedule.setAvailableSeatsNumber(dto.getAvailableSeatsNumber());
        schedule.setArrivalDateTime(dto.getArrivalDateTime());
        if (dto.getStation() != null)
            schedule.setStation(stationMapper.mapToEntity(dto.getStation()));
        return schedule;
    }
}
