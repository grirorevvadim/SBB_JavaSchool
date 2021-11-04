package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ScheduleDTO implements Serializable {
    private static final long serialVersionID = 12134153252L;
    private Long id;
    private TrainDTO trainId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalDateTime;
    private StationDTO station;
}
