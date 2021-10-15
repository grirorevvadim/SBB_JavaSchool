package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.StationDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.service.RootService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.mapper.RootMapper;
import com.tsystems.javaschool.projects.SBB.service.mapper.StationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("Roots")
public class RootController {
    private final RootService rootService;
    private final StationService stationService;
    private final StationMapper stationMapper;

    @GetMapping(path = "/{id}")
    public RootDTO getRoot(@PathVariable String id, Model model) {
        return rootService.getRootByRootId(id);
    }

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
        return "Roots";
    }

}
