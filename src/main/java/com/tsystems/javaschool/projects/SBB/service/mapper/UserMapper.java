package com.tsystems.javaschool.projects.SBB.service.mapper;


import com.tsystems.javaschool.projects.SBB.configuration.mapper.Mapper;
import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;

@Mapper
public class UserMapper {
    public UserDTO mapToDto(User user) {
        var dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setBirthDate(user.getBirthDate());
        dto.setWallet(user.getWallet());
        return dto;
    }

    public User mapToEntity(UserDTO dto) {
        var user = new User();
        user.setId(dto.getId());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setWallet(dto.getWallet());
        user.setBirthDate(dto.getBirthDate());
        return user;
    }
}
