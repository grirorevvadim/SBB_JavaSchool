package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationName;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("stations")
public class StationController {

    private final StationService stationService;

    @GetMapping(path = "/{id}")
    public StationDTO getStation(@PathVariable String id, Model model) {
        return stationService.getStationByStationId(id);
    }

    @PostMapping
    public StationDTO postStation(@ModelAttribute(name = "station") StationDTO stationDTO) {
        return stationService.createStation(stationDTO);
    }

//    @PutMapping(path = "/{id}")
//    public StationRest updateStation(@PathVariable String id, @RequestBody StationDetailsModel stationDetails) {
//        StationRest stationRest = new StationRest();
//        StationDTO stationDTO = new StationDTO();
//        BeanUtils.copyProperties(stationDetails, stationDTO);
//        stationDTO = stationService.updateStation(id, stationDTO);
//        BeanUtils.copyProperties(stationDTO, stationRest);
//        return stationRest;
//    }

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteStation(@PathVariable String id) {
        OperationStatus status = new OperationStatus();
        status.setOperationName(OperationName.DELETE.name());
        status.setOperationResult(stationService.deleteStation(id));
        return status;
    }
}
