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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User ticketOwner;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id")
    private Root root;
}
