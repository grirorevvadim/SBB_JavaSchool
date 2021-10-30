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
import org.springframework.validation.BindingResult;
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

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        RootDTO rootDTO = rootService.getRootByRootId(id);
        StationDTO stationDTO = new StationDTO();
        int stationIndex = 0;
        model.addAttribute("index", stationIndex);
        model.addAttribute("station", stationDTO);
        model.addAttribute("root", rootDTO);
        model.addAttribute("error","");
        return "update-root";
    }

    @PostMapping("/update/{id}")
    public String updateRoot(@PathVariable("id") long id,
                             @RequestParam(name = "stationName") String stationName,
                             @RequestParam(name = "index") int index,
                             @Valid RootDTO rootDTO,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            rootDTO.setId(id);
            return "update-root";
        }
        StationDTO stationDTO = stationService.getStationByStationName(stationName);
        RootDTO root = rootService.getRootByRootId(id);
        if (rootService.rootContainsStation(root,stationDTO)) {
            model.addAttribute("error", "This station has already been added to the root");
            model.addAttribute("index", index);
            model.addAttribute("station", stationDTO);
            model.addAttribute("root", root);
            return "update-root";
        }
        rootService.updateRoot(root, stationDTO, index);
        root = rootService.getRootByRootId(id);
        model.addAttribute("root", root);
        return "root";
    }

    @PostMapping("/remove/{id}")
    public String deleteStationFromRoot(@PathVariable("id") Long id,
                                        @RequestParam(name = "index") int index,
                                        @Valid RootDTO rootDTO,
                                        BindingResult result, Model model) {
        index = index - 1;
        if (result.hasErrors()) {
            rootDTO.setId(id);
            return "update-root";
        }
        RootDTO root = rootService.getRootByRootId(id);
        rootService.deleteStationRoot(root, index);
        root = rootService.getRootByRootId(id);
        model.addAttribute("root", root);
        return "root";
    }

    //"@{/roots/update/{id}(id=${root.id})?stationName='+${station.stationName}'+'&index='+index}"


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
