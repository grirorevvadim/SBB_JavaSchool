package com.tsystems.javaschool.projects.SBB.domain.dto;

import com.tsystems.javaschool.projects.SBB.domain.validation.NotUniqueEmail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionID = 221452353252L;
    private Long id;
    @NotBlank
    @Size(min = 4, max = 15)
    private String firstname;

    @NotBlank
    @Size(min = 2, max = 15)
    private String lastname;

    @NotBlank
    @Email
    @NotUniqueEmail
    @Size(min = 8, max = 40)
    private String email;

    private int wallet;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
