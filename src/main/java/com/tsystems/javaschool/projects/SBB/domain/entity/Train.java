package com.tsystems.javaschool.projects.SBB.domain.entity;

import com.tsystems.javaschool.projects.SBB.service.util.TrainType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "trains")
public class Train implements Serializable {
    private static final long serialVersionID = 53553531L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String trainId;

    @Column(nullable = false)
    private String arrivalId;

    @Column(nullable = false)
    private String departureId;

    @Column(nullable = false)
    private Integer allSeatsNumber;

    @Column(nullable = false)
    private Integer availableSeatsNumber;

    @Column(nullable = false)
    private TrainType trainType;

    @Column(nullable = false)
    private String scheduleId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Root root;
}
