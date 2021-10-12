package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.PathDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Path;

@Mapper
public class PathMapper {
    public PathDTO mapToDto(Path path) {
        var dto = new PathDTO();
        dto.setDepartureId(path.getDepartureId());
        dto.setDepartureId(path.getDepartureId());
        dto.setTravelTime(path.getTravelTime());
        return dto;
    }

    public Path mapToEntity(PathDTO dto) {
        var path = new Path();
        path.setDepartureId(dto.getDepartureId());
        path.setArrivalId(dto.getArrivalId());
        path.setTravelTime(dto.getTravelTime());
        return path;
    }
}
