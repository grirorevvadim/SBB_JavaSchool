package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    private static final long serialVersionID = 22145434653252L;
    private Long id;
    private UserDTO ticketOwner;
    private TrainDTO train;
    private Integer price;
    private ScheduleDTO departureSchedule;
    private ScheduleDTO arrivalSchedule;
}
