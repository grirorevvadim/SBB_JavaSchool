package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final Utils utils;
    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Transactional
    public void createStation(StationDTO dto) {
        var entity = stationMapper.mapToEntity(dto);
        entity.setStationId(utils.generateId(30));
        entity.setStationName(dto.getStationName());
        stationRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public StationDTO getStationByStationId(String id) {
        Station station = stationRepository.findByStationId(id);
        if (station == null) throw new RuntimeException("Station with id: " + id + " is not found");
        return stationMapper.mapToDto(station);
    }

    @Transactional(readOnly = true)
    public StationDTO getStationByStationName(String name) {
        Station station = stationRepository.findByStationName(name);
        if (station == null) throw new RuntimeException("Station with id: " + name + " is not found");
        return stationMapper.mapToDto(station);
    }

    @Transactional(readOnly = true)
    public List<StationDTO> getAllStations() {
        var stations = stationRepository.findAll();
        List<StationDTO> resultList = new ArrayList<>();
        for (Station station : stations) resultList.add(stationMapper.mapToDto(station));

        return resultList;
    }

    public StationDTO updateStation(String id, StationDTO station) {
        StationDTO stationDTO = new StationDTO();
        Station entity = stationRepository.findByStationId(id);
        if (entity == null) throw new RuntimeException("Station with id: " + id + " is not found");
        entity.setStationName(station.getStationName());

        Station stationEntity = stationRepository.save(entity);
        StationDTO resultStation = new StationDTO();

        BeanUtils.copyProperties(stationEntity, resultStation);
        return resultStation;
    }

    @Transactional
    public String deleteStation(String id) {
        Station station = stationRepository.findByStationId(id);

        if (station == null) throw new RuntimeException("Station with id: " + id + " is not found");

        String result;
        stationRepository.delete(station);
        station = stationRepository.findByStationId(id);
        if (station != null) {
            result = OperationStatusResponse.ERROR.name();
            throw new RuntimeException("User with id: " + id + " is not deleted");
        } else result = OperationStatusResponse.SUCCESS.name();
        return result;
    }
}
