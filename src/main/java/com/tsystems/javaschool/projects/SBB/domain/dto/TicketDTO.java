package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.Schedule;
import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    private static final long serialVersionID = 22145434653252L;
    private Long id;
    private String ticketId;
    private User ticketOwner;
    private Train train;
    private Schedule departureSchedule;
    private Schedule arrivalSchedule;
}
