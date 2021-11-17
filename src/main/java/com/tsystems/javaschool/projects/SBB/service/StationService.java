package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Transactional
    public void createStation(StationDTO dto) {
        var entity = stationMapper.mapToEntity(dto);
        stationRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public StationDTO findStationByStationId(Long id) {
        return stationRepository.findById(id)
                .map(stationMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Station with id: " + id + " is not found"));
    }

    @Transactional(readOnly = true)
    public StationDTO getStationByStationName(String name) {
        Station station = stationRepository.findByStationName(name);
        if (station == null) throw new EntityNotFoundException("Station with id: " + name + " is not found");
        return stationMapper.mapToDto(station);
    }

    @Transactional(readOnly = true)
    public List<StationDTO> getAllStations() {
        var stations = stationRepository.findAll();
        List<StationDTO> resultList = new ArrayList<>();
        for (Station station : stations) resultList.add(stationMapper.mapToDto(station));

        return resultList;
    }

    public void updateStation(Long id, StationDTO station) {
        Optional<Station> entity = stationRepository.findById(id);
        if (entity.isEmpty()) throw new EntityNotFoundException("Station with id: " + id + " is not found");
        Station result = entity.get();
        result.setStationName(station.getStationName());
        stationRepository.save(result);
    }

    @Transactional
    public void deleteStation(Long id) {
        Optional<Station> station = stationRepository.findById(id);
        if (station.isEmpty()) throw new EntityNotFoundException("Station with id: " + id + " is not found");
        stationRepository.delete(station.get());
    }
}
