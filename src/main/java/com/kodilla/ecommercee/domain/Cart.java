package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @ManyToMany
    @JoinTable(
            name = "CART_PRODUCTS",
            joinColumns = @JoinColumn(name = "CART_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<Product> cartProducts;
    @NotNull
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
}
