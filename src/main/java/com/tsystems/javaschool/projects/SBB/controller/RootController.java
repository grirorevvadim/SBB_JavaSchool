package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.PathDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.service.RootService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("roots")
public class RootController {
    private final RootService rootService;
    private final StationService stationService;
    private final StationMapper stationMapper;

    @GetMapping(path = "/{id}")
    public RootDTO getRoot(@PathVariable Long id, Model model) {
        return rootService.getRootByRootId(id);
    }

    @GetMapping("/signup")
    public String showRootsSearchForm(@ModelAttribute(name = "path") PathDTO pathDTO) {
        return "search-roots";
    }

    @PostMapping
    public void postRoot(@ModelAttribute(name = "root") RootDTO rootDTO) {
        rootService.createRoot(rootDTO);
    }

    @GetMapping()
    public String getRoots(@Valid @ModelAttribute(name = "path") PathDTO pathDTO, Model model) {
        StationDTO stationA = stationService.getStationByStationName(pathDTO.getDeparture());
        StationDTO stationB = stationService.getStationByStationName(pathDTO.getArrival());

        var rootsDtoList = rootService.searchRoots(stationMapper.mapToEntity(stationA), stationMapper.mapToEntity(stationB));
        model.addAttribute("roots", rootsDtoList);
        return "roots";
    }

    @GetMapping("/editor")
    public String getRootEditorForm(@ModelAttribute(name = "root") RootDTO rootDTO) {
        return "root-editor";
    }

    @GetMapping("/cform")
    public String getRootCreateForm(@ModelAttribute(name = "root") RootDTO rootDTO) {
        return "create-root";
    }

    @GetMapping("/gform")
    public String showRootSearchForm(@ModelAttribute(name = "root") RootDTO rootDTO) {
        return "search-root";
    }

    @GetMapping("/delete/{id}")
    public String deleteStation(@PathVariable("id") long id) {
        rootService.deleteRoot(id);
        return "redirect:/roots/editor";
    }

//    @GetMapping(params = {"departureName", "arrivalName"})
//    public String getRoots(@RequestParam String departureName, @RequestParam String arrivalName, Model model) {
//        StationDTO stationA = stationService.getStationByStationName(departureName);
//        StationDTO stationB = stationService.getStationByStationName(arrivalName);
//
//        var rootsDtoList = rootService.searchRoots(stationMapper.mapToEntity(stationA), stationMapper.mapToEntity(stationB));
//        model.addAttribute("roots", rootsDtoList);
//        return "roots";
//    }

}
