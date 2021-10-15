package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.entity.Root;
import com.tsystems.javaschool.projects.SBB.domain.entity.Station;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PathDTO implements Serializable {
    private static final long serialVersionID = 32145487353252L;
    private Long id;
    private Station departureId;
    private Station arrivalId;
    private int travelTime;
    private Root root;
}
