package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.ScheduleDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.StationService;
import com.tsystems.javaschool.projects.SBB.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final ScheduleService scheduleService;

    @GetMapping("/home")
    public String homePage(@ModelAttribute(name = "train") TrainDTO trainDTO, Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        scheduleService.notifyConsumer();
        return "home";
    }



    @GetMapping("/users")
    public String getUsers(Model model) {
        var userDTOList = userServiceImpl.getAllUsers();
        model.addAttribute("users", userDTOList);
        return "users";
    }

    @GetMapping("/user")
    public String login(@ModelAttribute(name = "user") UserDTO user) {
        return "add-user";
    }

    @PostMapping("/users")
    public String createUser(@Valid @ModelAttribute(name = "user") UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "add-user";
        }
        userServiceImpl.createUser(userDTO);
        return "redirect:/home";
    }

    @GetMapping("/users/info")
    public String getUserInfo(Principal principal, Model model) {
        UserDTO userDTO = userServiceImpl.findUserByEmail(principal.getName());
        model.addAttribute("user", userDTO);
        return "user-info";
    }

    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        UserDTO userDTO = userServiceImpl.getUserByUserId(id);
        model.addAttribute("user", userDTO);
        model.addAttribute("error", "");
        return "update-user";
    }

    @PostMapping("/users/update")
    public String updateUser(UserDTO userDTO, Model model) {
        UserDTO check = userServiceImpl.findUserByEmail(userDTO.getEmail());
        if (check != null && !(check.getId().equals(userDTO.getId()))) {
            model.addAttribute("user", userDTO);
            model.addAttribute("error", "User with such email has already been registered");
            return "update-user";
        }
        userDTO = userServiceImpl.updateUser(userDTO);

        model.addAttribute("user", userDTO);
        return "user-info";
    }
}
