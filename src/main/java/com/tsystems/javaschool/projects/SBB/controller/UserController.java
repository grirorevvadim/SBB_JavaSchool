package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.UserService;
import org.springframework.validation.BindingResult;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationName;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        var userDTOList = userService.getAllUsers();
        model.addAttribute("users", userDTOList);
        return "users";
    }

    @GetMapping("/create-user")
    public String login(@ModelAttribute(name = "user") UserDTO user) {
        return "add-user";
    }

//    @GetMapping("/users", path = "/{id}")
//    public UserDTO getUser(@PathVariable String id, Model model) {
//        return userService.getUserByUserId(id);
//    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute(name = "user") UserDTO userDTO, BindingResult result) {
        if(result.hasErrors()){
            return "add-user";
        }
        userService.createUser(userDTO);
        return "redirect:/users";
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

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteUser(@PathVariable String id) {
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setOperationName(OperationName.DELETE.name());
        operationStatus.setOperationResult(userService.deleteUser(id));
        return operationStatus;
    }
}
