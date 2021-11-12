package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.TrainDTO;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;



    @GetMapping("/home")
    public String homePage(@ModelAttribute(name = "train") TrainDTO trainDTO) {
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

//    @PutMapping(path = "/{id}")
//    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
//        UserRest userRest = new UserRest();
//        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(userDetails, userDTO);
//        UserDTO updatedUser = userService.updateUser(id, userDTO);
//        BeanUtils.copyProperties(updatedUser, userRest);
//
//        return userRest;
//    }

//    @DeleteMapping(path = "/{id}")
//    public OperationStatus deleteUser(@PathVariable String id) {
//        OperationStatus operationStatus = new OperationStatus();
//        operationStatus.setOperationName(OperationName.DELETE.name());
//        operationStatus.setOperationResult(userService.deleteUser(id));
//        return operationStatus;
//    }
}
