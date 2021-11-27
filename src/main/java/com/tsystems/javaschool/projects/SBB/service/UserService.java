package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void createUser(UserDTO user);

    UserDTO getUserByUserId(Long id);

    UserDTO updateUser(UserDTO user);

    void deleteUser(Long id);

    List<UserDTO> getAllUsers();

    UserDTO findUserByEmail(String email);

    void decreaseWalletAmount(UserDTO user, Integer price);

    void increaseWalletAmount(UserDTO user, Integer price);
}
