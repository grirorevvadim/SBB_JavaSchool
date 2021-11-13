package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ScheduleDTO implements Serializable {
    private static final long serialVersionID = 12134153252L;
    private Long id;
    private TrainDTO trainId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalDateTime;
    private StationDTO station;
    private Integer availableSeatsNumber;
    private List<UserDTO> usersList;
}
