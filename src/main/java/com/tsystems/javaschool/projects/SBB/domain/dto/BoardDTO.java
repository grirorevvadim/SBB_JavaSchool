package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BoardDTO implements Serializable {
    private TrainDTO train;
    private String arrivalDateTime;
    private StationDTO station;
}
