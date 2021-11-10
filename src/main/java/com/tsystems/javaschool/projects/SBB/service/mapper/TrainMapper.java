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
public class TrainMapper {
    private final RootMapper rootMapper;
    private final ScheduleMapper scheduleMapper;

    public TrainDTO mapToDto(Train train) {
        var dto = new TrainDTO();
        dto.setId(train.getId());
        dto.setAllSeatsNumber(train.getAllSeatsNumber());
        dto.setAvailableSeatsNumber(train.getAvailableSeatsNumber());
        dto.setTrainType(train.getTrainType());
        dto.setTrainNumber(train.getTrainNumber());
        dto.setSectionPrice(train.getSectionPrice());
        dto.setRoot(rootMapper.mapToDto(train.getRootId()));
        if (train.getScheduleList() != null) {
            List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
            for (Schedule schedule : train.getScheduleList()) {
                scheduleDTOS.add(scheduleMapper.mapToDto(schedule));
            }
            dto.setScheduleList(scheduleDTOS);
        }

        return dto;
    }

    public Train mapToEntity(TrainDTO dto) {
        var train = new Train();
        train.setId(dto.getId());
        train.setAllSeatsNumber(dto.getAllSeatsNumber());
        train.setAvailableSeatsNumber(dto.getAvailableSeatsNumber());
        train.setTrainType(dto.getTrainType());
        train.setTrainNumber(dto.getTrainNumber());
        train.setSectionPrice(dto.getSectionPrice());
        train.setRootId(rootMapper.mapToEntity(dto.getRoot()));
        if (dto.getScheduleList() != null) {
            List<Schedule> schedules = new ArrayList<>();
            for (ScheduleDTO scheduleDTO : dto.getScheduleList()) {
                schedules.add(scheduleMapper.mapToEntity(scheduleDTO));
            }
            train.setScheduleList(schedules);
        }

        return train;
    }
}
