package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity(name = "users")
public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53523521L;

    @Column(nullable = false, name = "user_id")
    private String userId;

    @Column(nullable = false, length = 50, name = "firstname")
    private String firstname;

    @Column(nullable = false, length = 50, name = "lastname")
    private String lastname;

    @Column(nullable = false, length = 120, unique = true, name = "email")
    private String email;

//    @Column
//    private String password;
//    @Column
//    private String encryptedPassword;
//    private String emailVerificationToken;
//
//    @Column(nullable = false)
//    private Boolean emailVerificationStatus = false;

    @Column(nullable = false, name = "birthdate")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "ticketOwner", cascade = CascadeType.ALL)
    private List<Ticket> tickets;


}
