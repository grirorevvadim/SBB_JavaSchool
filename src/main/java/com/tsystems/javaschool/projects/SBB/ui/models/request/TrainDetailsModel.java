package com.tsystems.javaschool.projects.SBB.ui.models.request;

import com.tsystems.javaschool.projects.SBB.ui.models.TrainType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainDetailsModel {
    private String trainId;
    private String arrivalId;
    private String departureId;
    private Integer allSeatsNumber;
    private Integer availableSeatsNumber;
    private TrainType trainType;
    private String scheduleId;
}
