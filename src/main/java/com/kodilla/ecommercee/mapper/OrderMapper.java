package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDto mapOrderToOrderDto(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getUser().getUserId(),
                order.getCart().getCartId(),
                order.getStatus().name(),
                order.getCreationDate(),
                order.getTotal()
        );
    }
}
