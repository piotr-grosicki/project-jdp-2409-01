package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "PRODUCT_ID", unique = true)
    private Long id;
    @NotNull
    @Column(name = "PRODUCT_NAME")
    private String name;
    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;
    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDateTime createdDate;
}
