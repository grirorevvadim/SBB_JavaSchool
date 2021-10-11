package com.tsystems.javaschool.projects.SBB.service.mapper;


import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;

@Mapper
public class UserMapper {
    public UserDTO mapToDto(User user) {
        var dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setBirthDate(user.getBirthDate());
        return dto;
    }
}
