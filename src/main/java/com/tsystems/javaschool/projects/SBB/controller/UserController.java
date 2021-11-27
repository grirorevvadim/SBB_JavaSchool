package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import com.tsystems.javaschool.projects.SBB.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public String startPage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(@ModelAttribute(name = "train") TrainDTO trainDTO, Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("user", principal.getName());
        }
        return "home";
    }


    @GetMapping("/users")
    public String getUsers(Model model, Principal principal) {
        var userDTOList = userServiceImpl.getAllUsers();
        model.addAttribute("users", userDTOList);
        model.addAttribute("loggedUser", principal.getName());
        return "users";
    }

    @GetMapping("/user")
    public String login(@ModelAttribute(name = "user") UserDTO user, Principal principal, Model model) {
        return "add-user";
    }

    @PostMapping("/users")
    public String createUser(@Valid @ModelAttribute(name = "user") UserDTO userDTO, BindingResult result, Model model, Principal principal) {
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
        model.addAttribute("loggedUser", principal.getName());
        return "user-info";
    }

    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, Principal principal) {
        UserDTO userDTO = userServiceImpl.getUserByUserId(id);
        model.addAttribute("user", userDTO);
        model.addAttribute("error", "");
        model.addAttribute("loggedUser", principal.getName());
        return "update-user";
    }

    @PostMapping("/users/update")
    public String updateUser(UserDTO userDTO, Model model, Principal principal) {
        UserDTO check = userServiceImpl.findUserByEmail(userDTO.getEmail());
        if (check != null && !(check.getId().equals(userDTO.getId()))) {
            model.addAttribute("user", userDTO);
            model.addAttribute("error", "User with such email has already been registered");
            model.addAttribute("loggedUser", principal.getName());
            return "update-user";
        }
        userDTO = userServiceImpl.updateUser(userDTO);

        model.addAttribute("user", userDTO);
        model.addAttribute("loggedUser", principal.getName());
        return "user-info";
    }
}
