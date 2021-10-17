package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class TrainDTO implements Serializable {
    private static final long serialVersionID = 21234346111252L;
    private Long id;
    private String trainId;
    private Integer allSeatsNumber;
    private Integer availableSeatsNumber;
    private TrainType trainType;
    private List<Schedule> scheduleList;
    private Root root;
}
