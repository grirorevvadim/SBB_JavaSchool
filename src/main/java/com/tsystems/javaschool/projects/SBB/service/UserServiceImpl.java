package com.tsystems.javaschool.projects.SBB.service;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import com.tsystems.javaschool.projects.SBB.exception.EntityNotFoundException;
import com.tsystems.javaschool.projects.SBB.repository.RoleRepository;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.mapper.UserMapper;
import com.tsystems.javaschool.projects.SBB.service.util.response.OperationStatusResponse;
import lombok.RequiredArgsConstructor;
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
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException("User with id: " + id + " is not found");
        return userMapper.mapToDto(user.get());
    }

    @Transactional
    public UserDTO updateUser(UserDTO user) {
        Optional<User> updatedUser = userRepository.findById(user.getId());
        if (updatedUser.isEmpty()) throw new EntityNotFoundException("User with id: " + user.getId() + " is not found");
        User resUser = updatedUser.get();
        if (user.getFirstname() != null) resUser.setFirstname(user.getFirstname());
        if (user.getLastname() != null) resUser.setLastname(user.getLastname());
        if (user.getBirthDate() != null) resUser.setBirthDate(user.getBirthDate());
        if (user.getEmail() != null) resUser.setEmail(user.getEmail());
        resUser.setWallet(user.getWallet());
        userRepository.save(resUser);
        return userMapper.mapToDto(resUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException("User with id: " + id + " is not found");
        userRepository.delete(user.get());
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

    @Transactional
    public void decreaseWalletAmount(UserDTO user, Integer price) {
        User user1 = userRepository.findByEmail(user.getEmail());
        user1.setWallet(user1.getWallet() - price);
        userRepository.save(user1);
    }

    @Transactional
    public void increaseWalletAmount(UserDTO user, Integer price) {
        User user1 = userRepository.findByEmail(user.getEmail());
        user1.setWallet(user1.getWallet() + price);
        userRepository.save(user1);
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
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRolesList());
    }

}
