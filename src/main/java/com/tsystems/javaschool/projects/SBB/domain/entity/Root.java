package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "roots")
public class Root extends AbstractEntity implements Serializable {

    @ManyToMany()
    @JoinTable(name = "roots_stations", joinColumns = @JoinColumn(name = "root_id"), inverseJoinColumns = @JoinColumn(name = "station_id"))
    private List<Station> stationsList;

    @Override
    public String toString() {
        StringBuilder route = new StringBuilder(stationsList.get(0).getStationName());
        for (int i = 1; i < stationsList.size(); i++) {
            route.append("-").append(stationsList.get(i).getStationName());
        }
        return route.toString();
    }
}

