package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO implements Serializable {
    private String trainNumber;
    private String arrivalDateTime;
    private String station;
}
