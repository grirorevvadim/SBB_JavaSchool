package com.tsystems.javaschool.projects.SBB.controllers;

import com.tsystems.javaschool.projects.SBB.service.UserService;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.ui.models.OperationStatus;
import com.tsystems.javaschool.projects.SBB.ui.models.request.UserDetailsRequestModel;
import com.tsystems.javaschool.projects.SBB.ui.models.response.OperationName;
import com.tsystems.javaschool.projects.SBB.ui.models.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserRest userRest = new UserRest();
        UserDTO userDTO = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDTO, userRest);
        return userRest;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDetails, userDTO);
        UserDTO createdUser = userService.createUser(userDTO);
        BeanUtils.copyProperties(createdUser, userRest);

        return userRest;
    }

    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDetails, userDTO);
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        BeanUtils.copyProperties(updatedUser, userRest);

        return userRest;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatus deleteUser(@PathVariable String id) {
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setOperationName(OperationName.DELETE.name());
        operationStatus.setOperationResult(userService.deleteUser(id));
        return operationStatus;
    }
}
