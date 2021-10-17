package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Schedule extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 63427531L;

    @Column(name = "schedule_id")
    private String scheduleId;

    @OneToOne()
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToOne()
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "arrival_date_time")
    private LocalDateTime arrivalDateTime;

}
