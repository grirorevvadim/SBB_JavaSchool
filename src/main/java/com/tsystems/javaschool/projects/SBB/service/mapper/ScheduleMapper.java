package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.BoardDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Mapper
@RequiredArgsConstructor
public class ScheduleMapper {
    private final StationMapper stationMapper;
    private final UserMapper userMapper;


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
        if (schedule.getUsersList() != null) {
            List<UserDTO> dtos = new ArrayList<>();
            for (User u : schedule.getUsersList()) {
                dtos.add(userMapper.mapToDto(u));
            }
            dto.setUsersList(dtos);
        }
        dto.setAvailableSeatsNumber(schedule.getAvailableSeatsNumber());
        dto.setId(schedule.getId());
        dto.setArrivalDateTime(schedule.getArrivalDateTime().toLocalDateTime());
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
        if (dto.getUsersList() != null) {
            List<User> users = new ArrayList<>();
            for (UserDTO u : dto.getUsersList()) {
                users.add(userMapper.mapToEntity(u));
            }
            schedule.setUsersList(users);
        }
        schedule.setAvailableSeatsNumber(dto.getAvailableSeatsNumber());
        schedule.setArrivalDateTime(dto.getArrivalDateTime().atZone(ZoneId.of("Europe/Moscow")));
        if (dto.getStation() != null)
            schedule.setStation(stationMapper.mapToEntity(dto.getStation()));
        return schedule;
    }


}
