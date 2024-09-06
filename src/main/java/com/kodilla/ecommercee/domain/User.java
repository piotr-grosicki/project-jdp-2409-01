package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
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
    @Column(name = "LOGIN(EMAIL)", unique = true)
    private String email;
    @NotNull
    @Column(name = "PASSWORD")
    private String password;
    @NotNull
    @Column(name = "STATUS")
    private String status;
    @NotNull
    @Column(name = "TEMPORARY_KEY")
    private int temporaryKey;
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDate createDate;
    @OneToMany(mappedBy = "USER_CARTS", cascade = CascadeType.ALL)
    private List<Cart> carts;
    @OneToMany(mappedBy = "USER_ORDERS", cascade = CascadeType.ALL)
    private List<Order> orders;
}
