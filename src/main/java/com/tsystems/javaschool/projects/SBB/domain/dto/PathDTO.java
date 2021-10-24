package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PathDTO {
    @NotBlank
    @Size(min = 3, max = 20)
    private String departure;

    @NotBlank
    @Size(min = 3, max = 20)
    private String arrival;
}
