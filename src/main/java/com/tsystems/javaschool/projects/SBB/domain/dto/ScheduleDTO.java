package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ScheduleDTO implements Serializable {
    private static final long serialVersionID = 12134153252L;
    private Long id;
    private TrainDTO trainId;
    private LocalDateTime arrivalDateTime;
    private StationDTO station;
}
