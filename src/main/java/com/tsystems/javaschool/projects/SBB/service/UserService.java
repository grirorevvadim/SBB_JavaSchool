package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.UserMapper;
import com.tsystems.javaschool.projects.SBB.service.util.Utils;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Utils utils;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(UserDTO user) {
        User entity = new User();
        BeanUtils.copyProperties(user, entity);
        entity.setUserId(utils.generateId(30));
        //  entity.setEncryptedPassword("test");

        User userEntity = userRepository.save(entity);
        UserDTO resultUser = new UserDTO();

        BeanUtils.copyProperties(userEntity, resultUser);
        return resultUser;
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByUserId(String id) {
        UserDTO resultUser = new UserDTO();
        User user = userRepository.findByUserId(id);

        if (user == null) throw new RuntimeException("User with id: " + id + " is not found");
        BeanUtils.copyProperties(user, resultUser);
        return resultUser;
    }

    @Transactional
    public UserDTO updateUser(String id, UserDTO user) {
        UserDTO resultUser = new UserDTO();
        User userEntity = userRepository.findByUserId(id);

        if (userEntity == null) throw new RuntimeException("User with id: " + id + " is not found");
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setBirthDate(user.getBirthDate());

        User updatedUser = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUser, resultUser);
        return resultUser;
    }

    @Transactional
    public String deleteUser(String id) {
        User user = userRepository.findByUserId(id);

        if (user == null) throw new RuntimeException("User with id: " + id + " is not found");

        String result;
        userRepository.delete(user);
        user = userRepository.findByUserId(id);
        if (user != null) {
            result = OperationStatusResponse.ERROR.name();
            throw new RuntimeException("User with id: " + id + " is not deleted");
        } else result = OperationStatusResponse.SUCCESS.name();
        return result;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            usersDTO.add(userMapper.mapToDto(user));
        }
        return usersDTO;
    }

    public UserDTO findUserByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return userMapper.mapToDto(user);
    }
}
