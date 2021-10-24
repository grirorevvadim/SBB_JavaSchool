package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("trains")
public class TrainController {
    private final StationService stationService;
    private final TrainService trainService;
    private final ScheduleService scheduleService;

    @GetMapping(path = "/{id}")
    public TrainDTO getTrain(@PathVariable Long id, Model model) {
        return trainService.getTrainByTrainId(id);
    }

    @GetMapping("/search")
    public String showTrains(@ModelAttribute(name = "train") TrainDTO trainDTO) {
        return "search-trains";
    }

    @PostMapping
    public void postTrain(@ModelAttribute(name = "train") TrainDTO trainDTO) {
        trainService.createTrain(trainDTO);
    }

    @GetMapping("/all")
    public String showAllTrains(Model model) {
        List<TrainDTO> dtoTrainsList = trainService.findAll();
        model.addAttribute("trains", dtoTrainsList);
        return "all-trains";
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
    public void deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
    }


    @GetMapping()
    public String getTrains(@Valid @ModelAttribute(name = "train") TrainDTO trainDTO, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "search-trains";
        StationDTO stationA = stationService.getStationByStationName(trainDTO.getDepartureName());
        StationDTO stationB = stationService.getStationByStationName(trainDTO.getArrivalName());

//        List<ScheduleDTO> departure = scheduleService.searchTrains(trainDTO.getDepartureName(), trainDTO);
//        departure = scheduleService.filterScheduleByDate(departure, trainDTO.getDepartureDate());
//        List<ScheduleDTO> arrival = scheduleService.searchTrains(trainDTO.getArrivalName(), trainDTO);
//        model.addAttribute("departures", departure);
//        model.addAttribute("arrivals", arrival);

        List<ScheduleDTO> departure = scheduleService.searchStationSchedule(trainDTO.getDepartureName(), trainDTO);
        departure = scheduleService.filterScheduleByDate(departure, trainDTO.getDepartureDate());
        List<ScheduleDTO> arrival = scheduleService.searchStationSchedule(trainDTO.getArrivalName(), trainDTO);
        model.addAttribute("departures", departure);
        model.addAttribute("arrivals", arrival);
        return "trains";
    }
}
