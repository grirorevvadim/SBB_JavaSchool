package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;

@Mapper
public class RootMapper {
    public RootDTO mapToDto(Root root) {
        var dto = new RootDTO();
        dto.setId(root.getId());
        dto.setStationsList(root.getStationsList());
        return dto;
    }

    public Root mapToEntity(RootDTO dto) {
        var root = new Root();
        root.setId(dto.getId());
        root.setStationsList(dto.getStationsList());
        return root;
    }
}
