package com.tsystems.javaschool.projects.SBB.service.mapper;


import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;

@Mapper
public class UserMapper {
    public UserDTO mapToDto(User user) {
        var dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setBirthDate(user.getBirthDate());
        return dto;
    }

    public User mapToEntity(UserDTO dto) {
        var user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        return user;
    }
}
