package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "schedules")
public class Schedule extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 63427531L;

    @ManyToOne()
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "arrival_date_time")
    private LocalDateTime arrivalDateTime;

}
