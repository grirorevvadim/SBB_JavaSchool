package com.tsystems.javaschool.projects.SBB.service.mapper;

import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;

@Mapper
public class RootMapper {
    public RootDTO mapToDto(Root root) {
        var dto = new RootDTO();
        dto.setPathsList(root.getLinkedPaths());
        return dto;
    }

    public Root mapToEntity(RootDTO dto) {
        var root = new Root();
        root.setLinkedPaths(dto.getPathsList());
        return root;
    }
}
