package com.tsystems.javaschool.projects.SBB.service.implementation;

import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.repository.StationRepository;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    Utils utils;

    @Autowired
    StationRepository stationRepository;

    @Override
    public StationDTO createStation(StationDTO station) {
        Station entity = new Station();
        BeanUtils.copyProperties(station, entity);
        entity.setStationId(utils.generateId(30));
        entity.setStationName(station.getStationName());

        Station stationEntity = stationRepository.save(entity);
        StationDTO resultStation = new StationDTO();

        BeanUtils.copyProperties(stationEntity, resultStation);
        return resultStation;
    }

    @Override
    public StationDTO getStationByStationId(String id) {
        StationDTO resultStation = new StationDTO();
        Station station = stationRepository.findByStationId(id);

        if (station == null) throw new RuntimeException("Station with id: " + id + " is not found");
        BeanUtils.copyProperties(station, resultStation);
        return resultStation;
    }

    @Override
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

    @Override
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
