package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "tickets")
public class Ticket extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53523531L;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User ticketOwner;

    @OneToOne()
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne()
    @JoinColumn(name = "departure_shedule_id")
    private Schedule departureSchedule;

    @ManyToOne()
    @JoinColumn(name = "arrival_shedule_id")
    private Schedule arrivalSchedule;
}
