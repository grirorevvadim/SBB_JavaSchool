package com.tsystems.javaschool.projects.SBB.controllers;

import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.shared.dataTransferObject.StationDTO;
import com.tsystems.javaschool.projects.SBB.ui.models.OperationStatus;
import com.tsystems.javaschool.projects.SBB.ui.models.request.StationDetailsModel;
import com.tsystems.javaschool.projects.SBB.ui.models.response.OperationName;
import com.tsystems.javaschool.projects.SBB.ui.models.response.StationRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stations")
public class StationController {

    @Autowired
    StationService stationService;

    @GetMapping(path = "/{id}")
    public StationRest getStation(@PathVariable String id) {
        StationRest stationRest = new StationRest();
        StationDTO stationDTO = stationService.getStationByStationId(id);
        BeanUtils.copyProperties(stationDTO, stationRest);
        return stationRest;
    }

    @GetMapping
    public StationRest getStationByName(@RequestParam String stationName) {
        StationRest stationRest = new StationRest();
        StationDTO stationDTO = stationService.getStationByStationName(stationName);
        BeanUtils.copyProperties(stationDTO, stationRest);
        return stationRest;
    }

    @PostMapping
    public StationRest postStation(@RequestBody StationDetailsModel stationDetails) {
        StationRest stationRest = new StationRest();
        StationDTO stationDTO = new StationDTO();
        BeanUtils.copyProperties(stationDetails, stationDTO);
        StationDTO createdStation = stationService.createStation(stationDTO);
        BeanUtils.copyProperties(createdStation, stationRest);
        return stationRest;
    }

    @PutMapping(path = "/{id}")
    public StationRest updateStation(@PathVariable String id, @RequestBody StationDetailsModel stationDetails) {
        StationRest stationRest = new StationRest();
        StationDTO stationDTO = new StationDTO();
        BeanUtils.copyProperties(stationDetails, stationDTO);
        stationDTO = stationService.updateStation(id, stationDTO);
        BeanUtils.copyProperties(stationDTO, stationRest);
        return stationRest;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteStation(@PathVariable String id) {
        OperationStatus status = new OperationStatus();
        status.setOperationName(OperationName.DELETE.name());
        status.setOperationResult(stationService.deleteStation(id));
        return status;
    }
}
