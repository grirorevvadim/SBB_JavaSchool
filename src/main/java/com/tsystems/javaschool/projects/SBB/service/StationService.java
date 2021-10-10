package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;

public interface StationService {
    StationDTO createStation(StationDTO request);

    StationDTO getStationByStationId(String id);

    StationDTO updateStation(String id, StationDTO station);

    String deleteStation(String id);
}
