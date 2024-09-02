package com.kodilla.ecommercee.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private Long userId;

    // Konstruktor bezargumentowy
    public OrderDto() {
    }

    // Konstruktor z wszystkimi argumentami
    public OrderDto(Long id, LocalDate orderDate, BigDecimal totalAmount, String status, Long userId) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.userId = userId;
    }

    //Generujemy zestawy getterów oraz setterów


    // Gettery
    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    // Settery
    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}