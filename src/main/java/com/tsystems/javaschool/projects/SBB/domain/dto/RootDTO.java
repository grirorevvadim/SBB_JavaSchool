package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RootDTO implements Serializable {
    private static final long serialVersionID = 37425487353252L;
    private Long id;
    private List<StationDTO> stationsList;

    @Override
    public String toString() {
        StringBuilder route = new StringBuilder(stationsList.get(0).getStationName());
        for (int i = 1; i < stationsList.size(); i++) {
            route.append("-").append(stationsList.get(i).getStationName());
        }
        return route.toString();
    }
}
