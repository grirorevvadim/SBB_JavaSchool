package com.tsystems.javaschool.projects.SBB.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity(name = "users")
public class User extends AbstractEntity implements Serializable {
    private static final long serialVersionID = 53523521L;

    @NotBlank
    @Column(nullable = false, length = 50, name = "firstname")
    private String firstname;

    @NotBlank
    @Column(nullable = false, length = 50, name = "lastname")
    private String lastname;

    @Email
    @NotBlank
    @Column(nullable = false, length = 120, name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

//    @Column
//    private String password;
//    @Column
//    private String encryptedPassword;
//    private String emailVerificationToken;
//
//    @Column(nullable = false)
//    private Boolean emailVerificationStatus = false;

    @Column(nullable = false, name = "birthdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "ticketOwner")
    private List<Ticket> tickets;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> rolesList;

}
