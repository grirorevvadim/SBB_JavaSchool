package com.tsystems.javaschool.projects.SBB.shared.dataTransferObject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionID = 221452353252L;
    private Long id;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private LocalDate birthDate;
}
