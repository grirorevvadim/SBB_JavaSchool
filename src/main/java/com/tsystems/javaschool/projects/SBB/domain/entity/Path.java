package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "paths")
public class Path implements Serializable {
    private static final long serialVersionID = 6168453531L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String pathId;

    @Column(nullable = false)
    private String departureId;

    @Column(nullable = false)
    private String arrivalId;

    @Column(nullable = false)
    private int travelTime;

}
