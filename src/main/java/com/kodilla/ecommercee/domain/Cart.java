package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CARTS")
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "CART_ID", unique = true)
    private Long cartId;
    @NotNull
    @Column(name = "FK_CART_USER_ID")
    private Long userId;
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
}
