package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO getUserByUserId(String id);

    UserDTO updateUser(String id, UserDTO user);

    String deleteUser(String id);

    List<UserDTO> getAllUsers();
}
