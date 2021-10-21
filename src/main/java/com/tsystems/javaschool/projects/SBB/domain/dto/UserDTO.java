package com.tsystems.javaschool.projects.SBB.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
