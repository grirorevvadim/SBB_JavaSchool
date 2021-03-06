package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.validation.NotPastDate;
import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
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

    @PositiveOrZero
    private Integer sectionPrice;
    private List<ScheduleDTO> scheduleList;
    private RootDTO root;
}
