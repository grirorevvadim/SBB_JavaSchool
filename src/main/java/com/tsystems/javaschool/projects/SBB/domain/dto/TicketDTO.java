package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.Train;
import com.tsystems.javaschool.projects.SBB.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    private static final long serialVersionID = 22145434653252L;
    private Long id;
    //Train should be added

    //User should be added
    private String ticketId;
    private User userId;
    private Train train;
}
