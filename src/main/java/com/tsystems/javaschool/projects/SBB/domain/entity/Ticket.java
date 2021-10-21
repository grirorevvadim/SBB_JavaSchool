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

    @Column(nullable = false, name = "ticket_id")
    private String ticketId;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User ticketOwner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_shedule_id")
    private Schedule departureSchedule;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "arrival_shedule_id")
    private Schedule arrivalSchedule;
}
