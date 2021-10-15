package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "paths")
public class Path extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 6168453531L;

    @Column(nullable = false, name = "path_id")
    private String pathId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_id")
    private Station departureId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "arrival_id")
    private Station arrivalId;

    @Column(nullable = false, name = "travel_time")
    private int travelTime;

//    @ManyToOne
//    @JoinColumn(name = "roots_id")
//    private Root root;

    @ManyToMany(mappedBy = "linkedPaths")
    private List<Root> roots;

    @Override
    public String toString() {
        return "Path{" +
                "pathId='" + pathId + '\'' +
                ", departureId=" + departureId +
                ", arrivalId=" + arrivalId +
                ", travelTime=" + travelTime +
                '}';
    }
}
