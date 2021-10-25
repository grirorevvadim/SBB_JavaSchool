package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class StationDTO implements Serializable {
    private static final long serialVersionID = 22145433353252L;
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    private String stationName;

    private RootDTO root;

    private int index;

    @Override
    public String toString() {
        return "StationDTO{" +
                "stationName='" + stationName + '\'' +
                '}';
    }
}
