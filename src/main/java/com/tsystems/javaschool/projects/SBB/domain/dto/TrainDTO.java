package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.validation.NotPastDate;
import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class TrainDTO implements Serializable {
    private static final long serialVersionID = 21234346111252L;
    private Long id;
    @Min(20)
    @Max(500)
    private Integer allSeatsNumber;
    @Min(20)
    @Max(500)
    private Integer availableSeatsNumber;
    @NotBlank
    @Size(min = 2, max = 40)
    private String departureName;

    @NotBlank
    @Size(min = 2, max = 40)
    private String arrivalName;

    @NotPastDate
    private String departureDate;
    private String arrivalDate;

    private String trainNumber;
    private TrainType trainType;
    private List<ScheduleDTO> scheduleList;
    private RootDTO root;
}
