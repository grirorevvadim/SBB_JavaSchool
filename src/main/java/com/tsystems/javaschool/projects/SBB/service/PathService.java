package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.PathDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Path;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.repository.PathRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.PathMapper;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PathService {
    private final PathRepository pathRepository;
    private final PathMapper pathMapper;
    private final Utils utils;

    @Transactional
    public void createPath(PathDTO pathDTO) {
        Path path = pathMapper.mapToEntity(pathDTO);
        path.setPathId(utils.generateId(20));
        pathRepository.save(path);
    }

    @Transactional(readOnly = true)
    public PathDTO getPathByPathId(String id) {
        Path path = pathRepository.findPathByPathId(id);
        if (path == null) throw new RuntimeException("Path with id " + id + " was not found");
        return pathMapper.mapToDto(path);
    }

    public boolean containsStation(Path path, Station station){
        boolean result = false;
        if (path.getDepartureId().equals( station) || path.getArrivalId().equals(station)) return true;
        return result;
    }
}
