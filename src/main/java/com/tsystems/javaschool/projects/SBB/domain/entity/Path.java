package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "paths")
public class Path extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 6168453531L;

    @Column(nullable = false)
    private String pathId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Station departureId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Station arrivalId;

    @Column(nullable = false)
    private int travelTime;

}
