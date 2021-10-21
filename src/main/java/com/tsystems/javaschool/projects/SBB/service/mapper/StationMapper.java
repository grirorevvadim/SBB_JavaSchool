package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;

@Mapper
public class StationMapper {
    public StationDTO mapToDto(Station station) {
        var dto = new StationDTO();
        dto.setId(station.getId());
        dto.setStationName(station.getStationName());
        return dto;
    }

    public Station mapToEntity(StationDTO dto) {
        var station = new Station();
        station.setId(dto.getId());
        station.setStationName(dto.getStationName());
        return station;
    }
}
