package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Entity(name = "users")
public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53523521L;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

//    @Column
//    private String password;
//    @Column
//    private String encryptedPassword;
//    private String emailVerificationToken;
//
//    @Column(nullable = false)
//    private Boolean emailVerificationStatus = false;

    @Column(nullable = false)
    private LocalDate birthDate;

}
