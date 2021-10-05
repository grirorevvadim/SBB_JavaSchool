package com.tsystems.javaschool.projects.SBB.ui.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsRequestModel {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
