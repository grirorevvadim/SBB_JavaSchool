package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionID = 221452353252L;
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthDate;
}
