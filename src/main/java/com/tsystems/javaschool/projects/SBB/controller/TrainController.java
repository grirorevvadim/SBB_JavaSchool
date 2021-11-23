package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.repository.TrainRepository;
import com.tsystems.javaschool.projects.SBB.service.RootService;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.mapper.TrainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("trains")
public class TrainController {
    private final TrainService trainService;
    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;
    private final ScheduleService scheduleService;
    private final RootService rootService;

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
        List<ScheduleDTO> arrival = scheduleService.searchArrivalSchedule(trainDTO.getArrivalName(), departure);
        List<Integer> prices = new ArrayList<>();
        for (ScheduleDTO scheduleDTO : arrival) {
            prices.add(trainService.getPrice(scheduleDTO.getTrainId().getTrainNumber(), trainDTO.getDepartureName(), trainDTO.getArrivalName()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        model.addAttribute("format", formatter);
        model.addAttribute("prices", prices);
        model.addAttribute("departures", departure);
        model.addAttribute("arrivals", arrival);
        model.addAttribute("reservationTimeLimit", LocalDateTime.now().plusMinutes(10));
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
        List<RootDTO> routes = rootService.getAllRoots();
        model.addAttribute("routes", routes);
        model.addAttribute("train", trainDTO);
        model.addAttribute("error", "");
        return "create-train";
    }

    @PostMapping
    public String postTrain(@ModelAttribute(name = "train") TrainDTO trainDTO, Model model) {
        Train check = trainRepository.findByTrainNumber(trainDTO.getTrainNumber());
        if (check != null && check.getId() != trainDTO.getId()) {
            List<RootDTO> routes = rootService.getAllRoots();
            model.addAttribute("routes", routes);
            model.addAttribute("train", trainDTO);
            model.addAttribute("error", "Train with such number has already exist");
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
        List<RootDTO> routes = rootService.getAllRoots();
        model.addAttribute("routes", routes);
        model.addAttribute("trainRes", trainDTO);
        model.addAttribute("error", "");
        return "update-train";
    }

    @PostMapping("/update/{id}")
    public String updateTrain(@PathVariable("id") long id, TrainDTO trainDTO, Model model) {
        TrainDTO check = trainService.getTrainByNumber(trainDTO.getTrainNumber());
        if (check != null && check.getId() != trainDTO.getId()) {
            List<RootDTO> routes = rootService.getAllRoots();
            model.addAttribute("routes", routes);
            model.addAttribute("trainRes", trainDTO);
            model.addAttribute("error", "Train with such number has already exist");
            return "update-train";
        }
        trainService.updateTrain(trainDTO);
        trainDTO = trainService.getTrainByNumber(trainDTO.getTrainNumber());
        model.addAttribute("trainRes", trainDTO);
        return "train";
    }

}
