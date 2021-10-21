package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Mapper
@RequiredArgsConstructor
public class RootMapper {
    private final StationMapper stationMapper;

    public RootDTO mapToDto(Root root) {
        var dto = new RootDTO();
        dto.setId(root.getId());
        List<StationDTO> dtos = new ArrayList<>();
        for (Station station : root.getStationsList()) {
            dtos.add(stationMapper.mapToDto(station));
        }
        dto.setStationsList(dtos);
        return dto;
    }

    public Root mapToEntity(RootDTO dto) {
        var root = new Root();
        root.setId(dto.getId());
        List<Station> stations = new ArrayList<>();
        for (StationDTO sdto : dto.getStationsList()) {
            stations.add(stationMapper.mapToEntity(sdto));
        }
        root.setStationsList(stations);
        return root;
    }
}
