package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StationDTO implements Serializable {
    private static final long serialVersionID = 22145433353252L;
    private Long id;
    private String stationId;
    private String stationName;
}
