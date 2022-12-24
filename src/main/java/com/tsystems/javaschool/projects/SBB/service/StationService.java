package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.shared.dataTransferObject.StationDTO;

public interface StationService {
    StationDTO createStation(StationDTO request);

    StationDTO getStationByStationId(String id);

    StationDTO updateStation(String id, StationDTO station);

    String deleteStation(String id);

    StationDTO getStationByStationName(String stationName);
}
