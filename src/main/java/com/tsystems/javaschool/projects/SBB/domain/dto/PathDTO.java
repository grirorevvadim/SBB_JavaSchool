package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PathDTO implements Serializable {
    private static final long serialVersionID = 32145487353252L;
    private Long id;
    private String departureId;
    private String arrivalId;
    private int travelTime;
}
