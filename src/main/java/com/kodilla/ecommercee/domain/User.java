package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long userId;
    @NotNull
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @NotNull
    @Column(name = "EMAIL", unique = true)
    private String email;
    @NotNull
    @Column(name = "PASSWORD")
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private UserStatus status;
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDate createDate;
}
