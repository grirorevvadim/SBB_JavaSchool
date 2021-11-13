package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "schedules")
public class Schedule extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 63427531L;

    @ManyToOne()
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "arrival_date_time")
    private LocalDateTime arrivalDateTime;

    @Column(nullable = false, name = "available_seats_number")
    private Integer availableSeatsNumber;

    @ManyToMany()
    @JoinTable(name = "schedules_users", joinColumns = @JoinColumn(name = "schedule_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersList;

}
