package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @GetMapping("/editor")
    public String getEditorPage() {
        return "schedule-editor";
    }

    @GetMapping("/gform")
    public String getScheduleForm(@ModelAttribute(name = "schedule") ScheduleDTO scheduleDTO) {
        return "get-schedule";
    }

    @GetMapping("/scheduleinfo")
    public String getScheduleByTrainNumber(Model model,
                                           @ModelAttribute(name = "schedule") ScheduleDTO scheduleDTO,
                                           @RequestParam("trainNumber") Optional<String> trainNumber,
                                           @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        String trainNo;
        if (scheduleDTO.getTrainId() == null)
            trainNo = trainNumber.get();
        else trainNo = scheduleDTO.getTrainId().getTrainNumber();
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByTrainNumber(trainNo);

        ArrayList<List<ScheduleDTO>> pagedSchedules = scheduleService.getPagedSchedules(schedules);
        model.addAttribute("schedulePage", currentPage);
        model.addAttribute("schedules", pagedSchedules.get(currentPage));
        model.addAttribute("pageNumbers", pagedSchedules);
        model.addAttribute("trainNumber", trainNo);
        return "all-schedules";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable("id") long id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules/editor";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        ScheduleDTO scheduleDTO = scheduleService.getScheduleByScheduleId(id);
        model.addAttribute("schedule", scheduleDTO);
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
}
