package com.tsystems.javaschool.projects.SBB.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "stations")
public class StationEntity implements Serializable {
    private static final long serialVersionID = 53527531L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String stationId;

    @Column(nullable = false)
    private String stationName;

}
