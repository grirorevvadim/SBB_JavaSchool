package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.RootDTO;
import com.tsystems.javaschool.projects.SBB.service.RootService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("Roots")
public class RootController {
    private final RootService rootService;

    @GetMapping(path = "/{id}")
    public RootDTO getRoot(@PathVariable String id, Model model) {
        return rootService.getRootByRootId(id);
    }

    @PostMapping
    public void postRoot(@ModelAttribute(name = "root") RootDTO rootDTO) {
        rootService.createRoot(rootDTO);
    }

}
