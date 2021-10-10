package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.ui.models.TrainType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TrainDTO implements Serializable {
    private static final long serialVersionID = 21234346111252L;
    private Long id;
    private String trainId;
    private String arrivalId;
    private String departureId;
    private Integer allSeatsNumber;
    private Integer availableSeatsNumber;
    private TrainType trainType;
    private String scheduleId;
}
