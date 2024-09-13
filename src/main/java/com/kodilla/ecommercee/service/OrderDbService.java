package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.InvalidOrderStatusException;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.controller.exception.OrderNotFoundException;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.controller.exception.CartNotFoundException;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class OrderDbService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(orderMapper::mapOrderToOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return orderMapper.mapOrderToOrderDto(order);
    }

    public OrderDto addOrder(Long orderId) throws InvalidOrderStatusException, OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new InvalidOrderStatusException(order.getStatus());
        }

        order.setStatus(OrderStatus.NEW);

        return orderMapper.mapOrderToOrderDto(orderRepository.save(order));
    }

    public OrderDto updateOrder(Long orderId, UpdateOrderDto updateOrderDto) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.setStatus(OrderStatus.valueOf(updateOrderDto.getStatus()));

        return orderMapper.mapOrderToOrderDto(orderRepository.save(order));
    }

    public boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        } else {
            return false;
        }
    }
}
