package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationName;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("trains")
public class TrainController {
    private final StationService stationService;
    private final TrainService trainService;
    private final ScheduleService scheduleService;

    @GetMapping(path = "/{id}")
    public TrainDTO getTrain(@PathVariable String id, Model model) {
        return trainService.getTrainByTrainId(id);
    }

    @GetMapping("/search")
    public String showTrains(@ModelAttribute(name = "train") TrainDTO trainDTO) {
        return "search-trains";
    }

    @PostMapping
    public TrainDTO postTrain(@ModelAttribute(name = "train") TrainDTO trainDTO) {
        return trainService.createTrain(trainDTO);
    }

//    @PutMapping(path = "/{id}")
//    public TrainRest updateTrain(@PathVariable String id, @RequestBody TrainDetailsModel trainDetails) {
//        TrainRest trainRest = new TrainRest();
//        TrainDTO trainDTO = new TrainDTO();
//        BeanUtils.copyProperties(trainDetails, trainDTO);
//        trainDTO = trainService.updateTrain(id, trainDTO);
//        BeanUtils.copyProperties(trainDTO, trainRest);
//        return trainRest;
//    }

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteTrain(@PathVariable String id) {
        OperationStatus status = new OperationStatus();
        status.setOperationName(OperationName.DELETE.name());
        status.setOperationResult(trainService.deleteTrain(id));
        return status;
    }


    @GetMapping()
    public String getTrains(@ModelAttribute(name = "train") TrainDTO trainDTO, Model model) {
        StationDTO stationA = stationService.getStationByStationName(trainDTO.getDepartureName());
        StationDTO stationB = stationService.getStationByStationName(trainDTO.getArrivalName());

        List<Schedule> res = scheduleService.searchTrains(trainDTO);
        model.addAttribute(res);

        return "trains";
    }
}
