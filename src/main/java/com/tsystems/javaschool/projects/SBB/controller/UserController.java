package com.tsystems.javaschool.projects.SBB.controller;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.UserService;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationName;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserDTO getUser(@PathVariable String id, Model model) {
        return userService.getUserByUserId(id);
    }

    @PostMapping
    public UserDTO createUser(@ModelAttribute(name = "user") UserDTO userDTO) {
        return userService.createUser(userDTO);
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
