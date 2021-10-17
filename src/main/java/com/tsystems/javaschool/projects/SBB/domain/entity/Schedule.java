package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity(name = "schedules")
public class Schedule extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 63427531L;

    @Column(name = "schedule_id")
    private String scheduleId;

    @ManyToOne()
    @JoinColumn(name = "train_id")
    private Train train_id;

    @OneToOne()
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "arrival_date_time")
    private LocalDateTime arrivalDateTime;

}
