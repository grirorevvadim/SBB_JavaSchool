package com.tsystems.javaschool.projects.SBB.domain.entity;

import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "trains")
public class Train extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53553531L;

    @Column(nullable = false, name = "train_id")
    private String trainId;

    @Column(nullable = false, name = "all_seats_number")
    private Integer allSeatsNumber;

    @Column(nullable = false, name = "available_seats_number")
    private Integer availableSeatsNumber;

    @Column(nullable = false, name = "train_type")
    private TrainType trainType;

    @Column(nullable = false, name = "schedule_id")
    private String scheduleId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id")
    private Root root;
}
