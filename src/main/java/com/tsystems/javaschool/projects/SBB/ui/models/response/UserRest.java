package com.tsystems.javaschool.projects.SBB.ui.models.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRest {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate birthDate;
}
