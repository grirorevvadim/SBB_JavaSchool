package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ScheduleDTO implements Serializable {
    private static final long serialVersionID = 12134153252L;
    private Long id;
    private Train trainId;
    private LocalDateTime arrivalDateTime;
    private Station station;
}
