package com.tsystems.javaschool.projects.SBB.service.implementation;

import com.tsystems.javaschool.projects.SBB.io.entity.UserEntity;
import com.tsystems.javaschool.projects.SBB.repository.UserRepository;
import com.tsystems.javaschool.projects.SBB.service.UserService;
import com.tsystems.javaschool.projects.SBB.shared.Utils;
import com.tsystems.javaschool.projects.SBB.shared.dataTransferObject.UserDTO;
import com.tsystems.javaschool.projects.SBB.ui.models.response.OperationStatusResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDTO createUser(UserDTO user) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(user, entity);
        entity.setUserId(utils.generateId(30));
        entity.setEncryptedPassword("test");

        UserEntity userEntity = userRepository.save(entity);
        UserDTO resultUser = new UserDTO();

        BeanUtils.copyProperties(userEntity, resultUser);
        return resultUser;
    }

    @Override
    public UserDTO getUserByUserId(String id) {
        UserDTO resultUser = new UserDTO();
        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) throw new RuntimeException("User with id: " + id + " is not found");
        BeanUtils.copyProperties(userEntity, resultUser);
        return resultUser;
    }

    @Override
    public UserDTO updateUser(String id, UserDTO user) {
        UserDTO resultUser = new UserDTO();
        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) throw new RuntimeException("User with id: " + id + " is not found");
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());

        UserEntity updatedUser = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUser, resultUser);
        return resultUser;
    }

    @Override
    public String deleteUser(String id) {
        UserEntity userEntity = userRepository.findByUserId(id);

        if (userEntity == null) throw new RuntimeException("User with id: " + id + " is not found");

        String result;
        userRepository.delete(userEntity);
        userEntity = userRepository.findByUserId(id);
        if (userEntity != null) {
            result = OperationStatusResponse.ERROR.name();
            throw new RuntimeException("User with id: " + id + " is not deleted");
        } else result = OperationStatusResponse.SUCCESS.name();
        return result;
    }
}
