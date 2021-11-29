package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Role;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import com.tsystems.javaschool.projects.SBB.repository.RoleRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    String encryptedPassword = "54321";
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserMapper userMapper;

    @Mock
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser() {
        Role role = new Role();
        role.setName("test");
        role.setId(123L);
        User user = new User();
        user.setRolesList(new ArrayList<>());
        when(roleRepository.findByName(anyString())).thenReturn(role);
        when(userMapper.mapToEntity(any(UserDTO.class))).thenReturn(user);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setEmail("test@test.com");
        userDTO1.setPassword("242141");
        userService.createUser(userDTO1);

        verify(userMapper, times(1)).mapToEntity(any(UserDTO.class));
        verify(roleRepository, times(1)).findByName(anyString());
        verify(bCryptPasswordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }
}