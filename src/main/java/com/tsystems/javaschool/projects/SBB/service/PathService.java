package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.PathDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Path;
import com.tsystems.javaschool.projects.SBB.repository.PathRepository;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PathService {
    private final PathRepository pathRepository;
    private final Utils utils;

    @Transactional
    public void createPath(PathDTO pathDTO) {
        Path path = new Path();
        BeanUtils.copyProperties(pathDTO, path);
        path.setPathId(utils.generateId(20));
        PathDTO resultPath = new PathDTO();
        BeanUtils.copyProperties(pathRepository.save(path), resultPath);
    }

    @Transactional(readOnly = true)
    public PathDTO getPathByPathId(String id) {
        PathDTO pathDTO = new PathDTO();
        Path path = pathRepository.findPathByPathId(id);
        if (path == null) throw new RuntimeException("Path with id " + id + " was not found");
        BeanUtils.copyProperties(path, pathDTO);
        return pathDTO;
    }
}
