package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Data;

@Data
public class PathDTO {
    private StationDTO departure;
    private StationDTO arrival;
}
