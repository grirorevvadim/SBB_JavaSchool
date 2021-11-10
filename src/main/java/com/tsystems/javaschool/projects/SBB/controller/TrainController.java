package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
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


    @GetMapping("/all")
    public String showAllTrains(Model model) {
        List<TrainDTO> dtoTrainsList = trainService.findAll();
        model.addAttribute("trains", dtoTrainsList);
        return "all-trains";
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
    }


    @GetMapping()
    public String getTrains(@Valid @ModelAttribute(name = "train") TrainDTO trainDTO, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "search-trains";
        List<ScheduleDTO> departure = scheduleService.searchStationSchedule(trainDTO.getDepartureName(), trainDTO);
        departure = scheduleService.filterScheduleByDate(departure, trainDTO.getDepartureDate());
        List<ScheduleDTO> arrival = scheduleService.searchStationSchedule(trainDTO.getArrivalName(), trainDTO);
        model.addAttribute("departures", departure);
        model.addAttribute("arrivals", arrival);
        return "trains";
    }

    @GetMapping("/editor")
    public String getTrainEditorForm(@ModelAttribute(name = "train") TrainDTO trainDTO) {
        return "trainEditor";
    }

    @GetMapping("/gform")
    public String showTrainSearchForm(@ModelAttribute(name = "train") TrainDTO trainDTO) {
        return "search-train";
    }

    @GetMapping("/cform")
    public String showTrainCreateForm(Model model) {
        TrainDTO trainDTO = new TrainDTO();
        trainDTO = trainService.prepareTrain(trainDTO);
        model.addAttribute("train", trainDTO);
        return "create-train";
    }

    @PostMapping
    public String postTrain(@Valid @ModelAttribute(name = "train") TrainDTO trainDTO, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "create-train";
        }
        trainDTO = trainService.createTrain(trainDTO);
        model.addAttribute("trainRes", trainDTO);
        return "train";
    }

    @GetMapping("/train")
    public String getTrainByNumber(@ModelAttribute(name = "train") TrainDTO trainDTO, Model model) {
        trainDTO = trainService.getTrainByNumber(trainDTO.getTrainNumber());
        model.addAttribute("trainRes", trainDTO);
        return "train";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrain(@PathVariable("id") long id) {
        trainService.deleteTrain(id);
        return "redirect:/stations/editor";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TrainDTO trainDTO = trainService.getTrainByTrainId(id);
        model.addAttribute("trainRes", trainDTO);
        return "update-train";
    }

    @PostMapping("/update/{id}")
    public String updateTrain(@PathVariable("id") long id, @Valid TrainDTO trainDTO,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            trainDTO.setId(id);
            return "update-train";
        }
        trainService.updateTrain(trainDTO);
        trainDTO = trainService.getTrainByNumber(trainDTO.getTrainNumber());
        model.addAttribute("trainRes", trainDTO);
        return "train";
    }

}
