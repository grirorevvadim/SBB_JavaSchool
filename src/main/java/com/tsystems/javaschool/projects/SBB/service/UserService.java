package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public void createUser(UserDTO user);
    public UserDTO getUserByUserId(Long id);
    public UserDTO updateUser(Long id, UserDTO user);
    public String deleteUser(Long id);
    public List<UserDTO> getAllUsers();
    public UserDTO findUserByEmail(String email);
}
