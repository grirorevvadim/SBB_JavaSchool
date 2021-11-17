package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.RootRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.RootMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RootService {
    private final RootRepository routeRepository;
    private final RootMapper rootMapper;
    private final StationService stationService;

    @Transactional
    public void createRoot(RootDTO dto) {
        Root root = rootMapper.mapToEntity(dto);
        routeRepository.save(root);
    }

    @Transactional(readOnly = true)
    public RootDTO getRootByRootId(Long id) {
        return routeRepository.findById(id)
                .map(rootMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Root with id = " + id + " is not found"));
    }

    @Transactional(readOnly = true)
    public List<Root> searchRoots(Station a, Station b) {
        var rootsTable = routeRepository.findAll();
        boolean resultFlag = false;
        List<Root> rootList = new ArrayList<>();
        for (Root root : rootsTable) {
            for (Station station : root.getStationsList()) {
                if (station.equals(a)) {
                    resultFlag = true;
                    break;
                }
            }
            if (resultFlag) {
                for (Station station : root.getStationsList()) {
                    if (station.equals(b)) {
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

    @Transactional(readOnly = true)
    public List<RootDTO> getAllRoots() {
        List<RootDTO> res = new ArrayList<>();
        for (Root root : routeRepository.findAll()) {
            res.add(rootMapper.mapToDto(root));
        }
        return res;
    }

    @Transactional
    public void deleteRoot(long id) {
        Optional<Root> root = routeRepository.findById(id);
        if (root.isEmpty()) throw new EntityNotFoundException("Route with id =" + id + " is not found");
        routeRepository.delete(root.get());
    }

    @Transactional
    public void updateRoot(RootDTO rootDTO, StationDTO stationDTO, int index) {
        List<StationDTO> stations = rootDTO.getStationsList();
        stations.add(index, stationDTO);
        rootDTO.setStationsList(stations);
        routeRepository.save(rootMapper.mapToEntity(rootDTO));
    }

    @Transactional
    public void deleteStationRoot(RootDTO root, int index) {
        List<StationDTO> stations = root.getStationsList();
        stations.remove(index);
        routeRepository.save(rootMapper.mapToEntity(root));
    }

    public boolean rootContainsStation(RootDTO root, StationDTO stationDTO) {
        boolean res = false;
        for (StationDTO stationDTO1 : root.getStationsList()) {
            if (stationDTO1.getStationName().equals(stationDTO.getStationName())) {
                res = true;
                break;
            }
        }
        return res;
    }

    public int stationIndex(RootDTO root, StationDTO stationDTO) {
        int res = 0;
        for (int i = 0; i < root.getStationsList().size(); i++) {
            if (root.getStationsList().get(i).getStationName().equals(stationDTO.getStationName())) {
                res = i;
                break;
            }
        }
        return res;
    }

    public RootDTO createRootByStationNames(RootDTO rootDTO) {
        var stationDTOS = rootDTO.getStationsList();
        List<StationDTO> resList = new ArrayList<>();
        for (StationDTO stationDTO : stationDTOS) {
            resList.add(stationService.getStationByStationName(stationDTO.getStationName()));
        }
        rootDTO.setStationsList(resList);
        return rootMapper.mapToDto(saveNewRoot(rootDTO));
    }

    @Transactional
    public Root saveNewRoot(RootDTO rootDTO) {
        Root root = rootMapper.mapToEntity(rootDTO);
        Long id = routeRepository.save(root).getId();
        root.setId(id);
        return root;
    }


//    @Transactional
//    public List<Root> searchRoots(Station a, Station b) {
//        var rootsTable = repository.findAll();
//        boolean resultFlag = false;
//        List<Root> rootList = new ArrayList<>();
//        for (Root root : rootsTable) {
//            for (Path path : root.getLinkedPaths()) {
//                if (pathService.containsStation(path, a)) {
//                    resultFlag = true;
//                    break;
//                }
//            }
//            if (resultFlag) {
//                for (Path path : root.getLinkedPaths()) {
//                    if (pathService.containsStation(path, b)) {
//                        rootList.add(root);
//                        break;
//                    } else {
//                        resultFlag = false;
//                    }
//                }
//            }
//        }
//        return rootList;
//    }


//    @Transactional
//    public List<Root> getRoots(Path path) {
//        var rootsTable = repository.findAll();
//        boolean resultFlag = false;
//        List<Root> rootList = new ArrayList<>();
//        for (Root root : rootsTable) {
//            for (Path p : root.getLinkedPaths()) {
//                if (pathService.containsStation(p, a)) {
//                    resultFlag = true;
//                    break;
//                }
//            }
//            if (resultFlag) {
//                for (Path p : root.getLinkedPaths()) {
//                    if (pathService.containsStation(p, b)) {
//                        rootList.add(root);
//                        break;
//                    } else {
//                        resultFlag = false;
//                    }
//                }
//            }
//        }
//        return rootList;
//    }

}
