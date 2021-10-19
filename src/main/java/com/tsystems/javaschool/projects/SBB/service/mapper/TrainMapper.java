package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;

@Mapper
public class TrainMapper {
    public TrainDTO mapToDto(Train train) {
        var dto = new TrainDTO();
        dto.setTrainId(train.getTrainId());
        dto.setAllSeatsNumber(train.getAllSeatsNumber());
        dto.setAvailableSeatsNumber(train.getAvailableSeatsNumber());
        dto.setTrainType(train.getTrainType());
        dto.setTrainNumber(train.getTrainNumber());

        return dto;
    }

    public Train mapToEntity(TrainDTO dto) {
        var train = new Train();
        train.setTrainId(dto.getTrainId());
        train.setAllSeatsNumber(dto.getAllSeatsNumber());
        train.setAvailableSeatsNumber(dto.getAvailableSeatsNumber());
        train.setTrainType(dto.getTrainType());
        train.setTrainNumber(dto.getTrainNumber());

        return train;
    }
}
