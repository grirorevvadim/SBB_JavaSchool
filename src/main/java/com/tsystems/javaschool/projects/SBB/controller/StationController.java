package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.service.RootService;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("stations")
public class StationController {

    private final StationService stationService;
    private final ScheduleService scheduleService;
    private final RootService rootService;

    @GetMapping(path = "/{id}")
    public StationDTO getStation(@PathVariable Long id) {
        return stationService.findStationByStationId(id);
    }

    @GetMapping("/all")
    public String stations(Model model, Principal principal) {
        var dtoList = stationService.getAllStations();
        model.addAttribute("stations", dtoList);
        model.addAttribute("loggedUser", principal.getName());
        return "stations";
    }

    @GetMapping("/gform")
    public String showStationSearchForm(@ModelAttribute(name = "station") StationDTO stationDTO,
                                        Model model, Principal principal) {
        model.addAttribute("loggedUser", principal.getName());
        return "search-station";
    }

    @GetMapping("/cform")
    public String createStationForm(@ModelAttribute(name = "station") StationDTO stationDTO,
                                    Model model, Principal principal) {
        model.addAttribute("loggedUser", principal.getName());
        return "post-station";
    }

    @GetMapping("/schedule")
    public String showScheduleByStationForm(@ModelAttribute(name = "station") StationDTO stationDTO, Principal principal, Model model) {
        if (principal != null)
            model.addAttribute("loggedUser", principal.getName());
        return "search-schedule";
    }

    @PostMapping("/addstation")
    public String addStation(@Valid StationDTO stationDTO, BindingResult bindingResult,
                             Model model, Principal principal) {
        if (bindingResult.hasErrors()) return "post-station";
        stationService.createStation(stationDTO);
        stationDTO = stationService.getStationByStationName(stationDTO.getStationName());
        model.addAttribute("station", stationDTO);
        model.addAttribute("loggedUser", principal.getName());
        return "station";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, Principal principal) {
        StationDTO stationDTO = stationService.findStationByStationId(id);
        model.addAttribute("station", stationDTO);
        model.addAttribute("loggedUser", principal.getName());
        return "update-station";
    }

    @GetMapping("/delete/{id}")
    public String deleteStation(@PathVariable("id") long id) {
        stationService.deleteStation(id);
        return "redirect:/stations/editor";
    }

    @PostMapping("/update/{id}")
    public String updateStation(@PathVariable("id") long id, @Valid StationDTO stationDTO,
                                BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            stationDTO.setId(id);
            model.addAttribute("loggedUser", principal.getName());
            return "update-station";
        }

        stationService.updateStation(id, stationDTO);
        return "redirect:/stations/editor";
    }

    @GetMapping("/scheduleinfo")
    public String getScheduleByStation(@ModelAttribute(name = "station") StationDTO stationDTO,
                                       Model model, Principal principal) {
        var scheduleDTOList = scheduleService.getSchedulesByStation(stationDTO);
        if (stationDTO.getStationName().isEmpty()) return "redirect:/stations/all";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        model.addAttribute("format", formatter);
        model.addAttribute("schedules", scheduleDTOList);
        if (principal != null)
            model.addAttribute("loggedUser", principal.getName());
        return "schedules";
    }

    @GetMapping("/station")
    public String showStationCreateForm(@ModelAttribute(name = "station") StationDTO stationDTO,
                                        Model model, Principal principal) {
        List<RootDTO> roots = rootService.getAllRoots();
        model.addAttribute("roots", roots);
        model.addAttribute("loggedUser", principal.getName());
        return "create-station";
    }

    @PostMapping("/station")
    public String selectRootPlaceForStation(@ModelAttribute(name = "station") StationDTO stationDTO,
                                            Model model, Principal principal) {
        List<StationDTO> stations = stationDTO.getRoot().getStationsList();
        RootDTO rootDTO = stationDTO.getRoot();
        model.addAttribute("root", rootDTO);
        model.addAttribute("stations", stations);
        model.addAttribute("loggedUser", principal.getName());
        return "select-station-location";
    }

    @GetMapping("/editor")
    public String getStationEditorForm(@ModelAttribute(name = "station") StationDTO stationDTO,
                                       Model model, Principal principal) {
        model.addAttribute("loggedUser", principal.getName());
        return "stationEditor";
    }

    @GetMapping()
    public String getStationByName(@ModelAttribute(name = "stationName") StationDTO stationDTO,
                                   Model model, Principal principal) {
        var station = stationService.getStationByStationName(stationDTO.getStationName());
        model.addAttribute("station", station);
        model.addAttribute("loggedUser", principal.getName());
        return "station";
    }

    @PostMapping
    public void postStation(@ModelAttribute(name = "station") StationDTO stationDTO) {
        stationService.createStation(stationDTO);
    }
}
