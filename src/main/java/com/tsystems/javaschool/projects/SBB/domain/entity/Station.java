package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "stations")
public class Station extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53527531L;

    @Column(nullable = false, name = "station_name")
    private String stationName;

    @ManyToMany(mappedBy = "stationsList")
    private List<Root> roots;

    @OneToMany(mappedBy = "station")
    private List<Schedule> scheduleList;

    public Station() {

    }

    public Station(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(getId(), station.getId()) && Objects.equals(stationName, station.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), stationName);
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationName='" + stationName + '\'' +
                '}';
    }
}
