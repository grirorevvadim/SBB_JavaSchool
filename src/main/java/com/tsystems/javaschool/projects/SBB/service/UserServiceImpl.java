package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.Role;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.RoleRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.UserMapper;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public void createUser(UserDTO user) {
        User entity = userMapper.mapToEntity(user);
        entity.setRolesList(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User userEntity = userRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByUserId(Long id) {
        UserDTO resultUser = new UserDTO();
        User user = userRepository.getById(id);

        if (user == null) throw new EntityNotFoundException("User with id: " + id + " is not found");
        BeanUtils.copyProperties(user, resultUser);
        return resultUser;
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO user) {
        UserDTO resultUser = new UserDTO();
        User userEntity = userRepository.getById(id);

        if (userEntity == null) throw new EntityNotFoundException("User with id: " + id + " is not found");
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        userEntity.setBirthDate(user.getBirthDate());

        User updatedUser = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUser, resultUser);
        return resultUser;
    }

    @Transactional
    public String deleteUser(Long id) {
        User user = userRepository.getById(id);

        if (user == null) throw new EntityNotFoundException("User with id: " + id + " is not found");

        String result;
        userRepository.delete(user);
        Optional<User> entity = userRepository.findById(id);
        if (entity.isEmpty()) {
            result = OperationStatusResponse.ERROR.name();
            throw new EntityNotFoundException("User with id: " + id + " is not deleted");
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
        if (user == null) return null;
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
