package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Path;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.repository.RootRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.RootMapper;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RootService {
    private final RootRepository repository;
    private final RootMapper rootMapper;
    private final Utils utils;
    private final PathService pathService;

    @Transactional
    public void createRoot(RootDTO dto) {
        Root root = rootMapper.mapToEntity(dto);
        root.setRootId(utils.generateId(20));
        repository.save(root);
    }

    @Transactional
    public RootDTO getRootByRootId(String id) {
        var root = repository.findRootByRootId(id);
        if (root == null) throw new RuntimeException("Root with id " + id + " was not found");
        return rootMapper.mapToDto(root);
    }

    @Transactional
    public List<Root> searchRoots(Station a, Station b) {
        var rootsTable = repository.findAll();
        boolean resultFlag = false;
        List<Root> rootList = new ArrayList<>();
        for (Root root : rootsTable) {
            for (Path path : root.getPathsList()) {
                if (pathService.containsStation(path, a)) {
                    resultFlag = true;
                    break;
                }
            }
            if (resultFlag) {
                for (Path path : root.getPathsList()) {
                    if (pathService.containsStation(path, b)) {
                        rootList.add(root);
                        break;
                    } else {
                        resultFlag = false;
                    }
                }
            }
        }
        return rootList;
    }

}
