package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @NotNull
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @NotNull
    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal total;
    @NotNull
    @Column(name = "ORDER_DATE")
    private LocalDateTime createDate;
    @NotNull
    @Column(name = "STATUS")
    private OrderStatus status;
    @OneToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

}