package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.PathDTO;
import com.tsystems.javaschool.projects.SBB.service.PathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("path")
public class PathController {
    private final PathService pathService;

    @GetMapping(path = "/{id}")
    public String getPath(@PathVariable String id, Model model) {
        var path = pathService.getPathByPathId(id);
        model.addAttribute("path", path);
        return "path";
    }

    @PostMapping
    public String postPath(@ModelAttribute(name = "path") PathDTO pathDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "add-user";
        }
        pathService.createPath(pathDTO);
        return "redirect:paths";
    }
}
