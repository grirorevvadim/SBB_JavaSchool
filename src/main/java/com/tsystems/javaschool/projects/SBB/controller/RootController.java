package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.service.RootService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("roots")
public class RootController {
    private final RootService rootService;
    private final StationService stationService;
    private final StationMapper stationMapper;

    @GetMapping(path = "/{id}")
    public RootDTO getRoot(@PathVariable String id, Model model) {
        return rootService.getRootByRootId(id);
    }

//    @GetMapping("/signup")
//    public String showRootsSearchForm(@ModelAttribute(name = "root")RootDTO rootDTO){
//        return "search-roots";
//    }

    @PostMapping
    public void postRoot(@ModelAttribute(name = "root") RootDTO rootDTO) {
        rootService.createRoot(rootDTO);
    }

    @GetMapping(params = {"departure", "arrival"})
    public String getRoots(@RequestParam String departure, @RequestParam String arrival, Model model) {
        StationDTO stationA = stationService.getStationByStationId(departure);
        StationDTO stationB = stationService.getStationByStationId(arrival);

        var rootsDtoList = rootService.searchRoots(stationMapper.mapToEntity(stationA), stationMapper.mapToEntity(stationB));
        model.addAttribute("roots", rootsDtoList);
        return "roots";
    }

}
