package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class OrderDto {
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Schema(description = "Date when the order was placed", example = "2023-09-04")
    private LocalDate orderDate;

    @Schema(description = "Total amount of the order", example = "199.99")
    private BigDecimal totalAmount;

    @Schema(description = "Status of the order", example = "COMPLETED")
    private String status;

    @Schema(description = "User identifier associated with the order", example = "1")
    private Long userId;

    // Konstruktor bezargumentowy
    public OrderDto() {
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