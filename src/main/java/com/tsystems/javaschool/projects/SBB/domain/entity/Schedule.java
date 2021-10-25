package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne()
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "arrival_date_time")
    private LocalDateTime arrivalDateTime;

}
