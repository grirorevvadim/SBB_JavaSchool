package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.service.RootService;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationName;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("stations")
public class StationController {

    private final StationService stationService;
    private final ScheduleService scheduleService;
    private final RootService rootService;

    @GetMapping(path = "/{id}")
    public StationDTO getStation(@PathVariable Long id, Model model) {
        return stationService.getStationByStationId(id);
    }

    @GetMapping("/all")
    public String stations(Model model) {
        var dtoList = stationService.getAllStations();
        model.addAttribute("stations", dtoList);
        return "stations";
    }

    @GetMapping("/gform")
    public String showStationSearchForm(@ModelAttribute(name = "station") StationDTO stationDTO) {
        return "search-station";
    }

    @GetMapping("/cform")
    public String createStationForm(@ModelAttribute(name = "station") StationDTO stationDTO) {
        return "post-station";
    }

    @GetMapping("/schedule")
    public String showScheduleByStationForm(@ModelAttribute(name = "station") StationDTO stationDTO) {
        return "search-schedule";
    }

    @PostMapping("/addstation")
    public String addStation(@Valid StationDTO stationDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "post-station";
        stationService.createStation(stationDTO);
        stationDTO = stationService.getStationByStationName(stationDTO.getStationName());
        model.addAttribute("station", stationDTO);
        return "station";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        StationDTO stationDTO = stationService.getStationByStationId(id);
        model.addAttribute("station", stationDTO);
        return "update-station";
    }

    @GetMapping("/delete/{id}")
    public String deleteStation(@PathVariable("id") long id) {
        stationService.deleteStation(id);
        return "redirect:/stations/editor";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid StationDTO stationDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            stationDTO.setId(id);
            return "update-station";
        }

        stationService.updateStation(id,stationDTO);
        return "redirect:/stations/editor";
    }

    @GetMapping("/scheduleinfo")
    public String getScheduleByStation(@ModelAttribute(name = "station") StationDTO stationDTO, Model model) {
        var scheduleDTOList = scheduleService.getSchedulesByStation(stationDTO);
        model.addAttribute("schedules", scheduleDTOList);
        return "schedules";
    }

    @GetMapping("/station")
    public String showStationCreateForm(@ModelAttribute(name = "station") StationDTO stationDTO, Model model) {
        List<RootDTO> roots = rootService.getAllRoots();
        model.addAttribute("roots", roots);
        return "create-station";
    }

    @PostMapping("/station")
    public String selectRootPlaceForStation(@ModelAttribute(name = "station") StationDTO stationDTO, Model model) {
        List<StationDTO> stations = stationDTO.getRoot().getStationsList();
        RootDTO rootDTO = stationDTO.getRoot();
        model.addAttribute("root", rootDTO);
        model.addAttribute("stations", stations);
        return "select-station-location";
    }

    @GetMapping("/editor")
    public String getStationEditorForm(@ModelAttribute(name = "station") StationDTO stationDTO) {
        return "stationEditor";
    }

    @GetMapping()
    public String getStationByName(@ModelAttribute(name = "stationName") StationDTO stationDTO, Model model) {
        var station = stationService.getStationByStationName(stationDTO.getStationName());
        model.addAttribute("station", station);
        return "station";
    }

    @PostMapping
    public void postStation(@ModelAttribute(name = "station") StationDTO stationDTO) {
        stationService.createStation(stationDTO);
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

//    @DeleteMapping(path = "/{id}")
//    public OperationStatus deleteStation(@PathVariable Long id) {
//        var status = new OperationStatus();
//        status.setOperationName(OperationName.DELETE.name());
//        status.setOperationResult(stationService.deleteStation(id));
//        return status;
//    }
}
