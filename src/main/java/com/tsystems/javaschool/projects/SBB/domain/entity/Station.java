package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "stations")
public class Station extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53527531L;

    @Column(nullable = false, name = "station_id")
    private String stationId;

    @Column(nullable = false, name = "station_name")
    private String stationName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(stationId, station.stationId) && Objects.equals(stationName, station.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId, stationName);
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationName='" + stationName + '\'' +
                '}';
    }
}
