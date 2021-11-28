package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.TrainService;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final TrainService trainService;
    private final StationService stationService;

    @GetMapping("/editor")
    public String getEditorPage(Model model, Principal principal) {
        model.addAttribute("loggedUser", principal.getName());
        return "schedule-editor";
    }

    @GetMapping("/gform")
    public String getScheduleForm(@ModelAttribute(name = "schedule") ScheduleDTO scheduleDTO,
                                  Model model, Principal principal) {
        model.addAttribute("loggedUser", principal.getName());
        return "get-schedule";
    }

    @GetMapping("/scheduleinfo")
    public String getScheduleByTrainNumber(Model model,
                                           @ModelAttribute(name = "schedule") ScheduleDTO scheduleDTO,
                                           @RequestParam("trainNumber") Optional<String> trainNumber,
                                           @RequestParam("page") Optional<Integer> page, Principal principal) {
        int currentPage = page.orElse(0);
        String trainNo;
        if (scheduleDTO.getTrainId() == null) {
            trainNo = trainNumber.get();
        } else trainNo = scheduleDTO.getTrainId().getTrainNumber();
        var schedules = scheduleService.getSchedulesByTrainNumber(trainNo);
        if (!scheduleDTO.getTrainId().getDepartureDate().isEmpty())
            schedules = scheduleService.filterScheduleByDate(schedules, scheduleDTO.getTrainId().getDepartureDate());

            var pagedSchedules = scheduleService.getPagedSchedules(schedules);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        model.addAttribute("format", formatter);
        model.addAttribute("schedulePage", currentPage);
        model.addAttribute("schedules", pagedSchedules.get(currentPage));
        model.addAttribute("pageNumbers", pagedSchedules);
        model.addAttribute("trainNumber", trainNo);
        model.addAttribute("loggedUser", principal.getName());
        return "all-schedules";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable("id") long id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules/editor";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, Principal principal) {
        ScheduleDTO scheduleDTO = scheduleService.getScheduleByScheduleId(id);
        model.addAttribute("schedule", scheduleDTO);
        model.addAttribute("loggedUser", principal.getName());
        return "update-schedule";
    }

    @PostMapping("/update/{id}")
    public String editSchedule(@PathVariable("id") long id, @Valid ScheduleDTO scheduleDTO, Model model) {
        Schedule schedule = scheduleMapper.mapToEntity(scheduleDTO);
        schedule.setId(id);
        schedule = scheduleService.updateSchedule(schedule);
        ScheduleDTO resSchedule = scheduleMapper.mapToDto(schedule);
        model.addAttribute("resSchedule", resSchedule);
        return "redirect:/schedules/editor";
    }

    @GetMapping("/cform")
    public String getScheduleCreateForm(@ModelAttribute(name = "schedule") ScheduleDTO scheduleDTO,
                                        Model model, Principal principal) {
        model.addAttribute("loggedUser", principal.getName());
        return "select-train";
    }

    @GetMapping()
    public String fillInDatesForm(@ModelAttribute(name = "schedule") ScheduleDTO scheduleDTO,
                                  Model model, Principal principal) {
        var train = trainService.getTrainByNumber(scheduleDTO.getTrainId().getTrainNumber());
        var stations = train.getRoot().getStationsList();
        List<ScheduleDTO> schedules = new ArrayList<>();
        for (StationDTO s : stations) {
            schedules.add(new ScheduleDTO());
        }
        for (int i = 0; i < schedules.size(); i++) {
            schedules.get(i).setStation(stations.get(i));
            schedules.get(i).setTrainId(scheduleDTO.getTrainId());
        }
        var trainDTO = new TrainDTO();
        trainDTO.setTrainNumber(train.getTrainNumber());
        trainDTO.setScheduleList(schedules);
        model.addAttribute("train", trainDTO);
        model.addAttribute("loggedUser", principal.getName());
        return "fill-schedule-dates";
    }

    @PostMapping("/create")
    public String createSchedules(@ModelAttribute(name = "train") TrainDTO trainDTO,
                                  @RequestParam(name = "trainNumber") String trainNumber,
                                  Model model, Principal principal) {
        var schedules = trainDTO.getScheduleList();
        var train = trainService.getTrainByNumber(trainNumber);
        var stations = train.getRoot().getStationsList();
        for (StationDTO stationDTO : stations) {
            stationDTO.setId(stationService.getStationByStationName(stationDTO.getStationName()).getId());
        }
        for (int i = 0; i < schedules.size(); i++) {
            schedules.get(i).setTrainId(train);
            schedules.get(i).setAvailableSeatsNumber(train.getAllSeatsNumber());
            schedules.get(i).setStation(stations.get(i));
            scheduleService.createSchedule(schedules.get(i));
        }
        List<Integer> paged = new ArrayList<>();
        paged.add(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        model.addAttribute("format", formatter);
        model.addAttribute("schedulePage", 0);
        model.addAttribute("schedules", schedules);
        model.addAttribute("pageNumbers", paged);
        model.addAttribute("trainNumber", trainDTO.getTrainNumber());
        model.addAttribute("loggedUser", principal.getName());
        return "all-schedules";
    }
}
