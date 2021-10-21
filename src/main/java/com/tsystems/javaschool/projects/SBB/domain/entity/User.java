package com.tsystems.javaschool.projects.SBB.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity(name = "users")
public class User implements Serializable {
    private static final long serialVersionID = 53523521L;

    @Id
    @Column(nullable = false, name = "user_id")
    private String userId;

    @Column(nullable = false, length = 50, name = "firstname")
    private String firstname;

    @Column(nullable = false, length = 50, name = "lastname")
    private String lastname;

    @Column(nullable = false, length = 120, name = "email")
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
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

//    @OneToMany(mappedBy = "ticketOwner")
//    private List<Ticket> tickets;


}
