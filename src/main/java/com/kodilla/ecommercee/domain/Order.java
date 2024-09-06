package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @NotNull
    private int orderId;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;
    @NotNull
    @Column(name = "TOTAL_AMOUNT")
    private double total;
    @NotNull
    @Column(name = "ORDER_DATE")
    private LocalDate createDate;
    @NotNull
    @Column(name = "STATUS")
    private String status;
    @OneToOne
    @JoinColumn(name = "CART_ID")
    private Cart cartId;

}