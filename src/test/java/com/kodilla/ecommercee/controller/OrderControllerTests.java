package com.kodilla.ecommercee.controller;

import com.google.gson.Gson;
import com.kodilla.ecommercee.controller.exception.InvalidOrderStatusException;
import com.kodilla.ecommercee.controller.exception.OrderNotFoundException;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.service.EventDbService;
import com.kodilla.ecommercee.service.EventDetailDbService;
import com.kodilla.ecommercee.service.OrderDbService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DisplayName("Tests for OrderController class")
@SpringJUnitWebConfig
@WebMvcTest(OrderController.class)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderDbService orderDbService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private OrderMapper orderMapper;
    @MockBean
    private EventDbService eventDbService;
    @MockBean
    private EventDetailDbService eventDetailDbService;

    @DisplayName("Test case for no orders available")
    @Test
    void shouldFetchEmptyList() throws Exception {
        // Given
        Mockito.when(orderDbService.getAllOrders()).thenReturn(List.of());
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/orders"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @DisplayName("Test case for fetching orders")
    @Test
    void shouldFetchOrders() throws Exception {
        // Given
        OrderDto orderDto = new OrderDto(1L, 2L, 3L, OrderStatus.NEW.name(), LocalDateTime.of(2024, 9, 12, 12 ,0 ,0) , new BigDecimal("111.11"));

        Mockito.when(orderDbService.getAllOrders()).thenReturn(List.of(orderDto));
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/orders"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cartId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is("NEW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].creationDate", Matchers.is("2024-09-12T12:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalAmount", Matchers.is(111.11)));
    }

    @DisplayName("Test case for fetching an order by ID")
    @Test
    void shouldFetchOrderById() throws Exception {
        // Given
        Long orderId = 1L;
        OrderDto orderDto = new OrderDto(orderId, 2L, 3L, OrderStatus.NEW.name(), LocalDateTime.of(2024, 9, 12, 12, 0, 0), new BigDecimal("111.11"));

        Mockito.when(orderDbService.getOrderById(orderId)).thenReturn(orderDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("NEW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2024-09-12T12:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount", Matchers.is(111.11)));
    }

    @DisplayName("Test case for fetching an order by ID when order does not exist")
    @Test
    void shouldReturnOrderNotFoundException() throws Exception {
        // Given
        Long orderId = 1L;
        OrderDto orderDto = new OrderDto(orderId, 2L, 3L, OrderStatus.NEW.name(), LocalDateTime.of(2024, 9, 12, 12, 0, 0), new BigDecimal("111.11"));

        Mockito.when(orderDbService.getOrderById(orderId)).thenThrow(new OrderNotFoundException(orderId));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("order.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Order with ID 1 not found.")));
    }

    @DisplayName("Test case for adding an order")
    @Test
    void shouldAddOrder() throws Exception {
        // Given
        Long orderId = 1L;
        OrderDto addedOrderDto = new OrderDto(orderId, 2L, 3L, OrderStatus.NEW.name(), LocalDateTime.of(2024, 9, 12, 12 ,0 ,0) , new BigDecimal("111.11"));

        Mockito.when(orderDbService.addOrder(orderId)).thenReturn(addedOrderDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("NEW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2024-09-12T12:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount", Matchers.is(111.11)));
    }

    @DisplayName("Test case for adding an order by ID when order does not exist")
    @Test
    void shouldReturnOrderNotFoundExceptionWhenAddingOrderWhichDoesNotExists() throws Exception {
        // Given
        Long orderId = 1L;

        Mockito.when(orderDbService.addOrder(orderId)).thenThrow(new OrderNotFoundException(orderId));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("order.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Order with ID 1 not found.")));
    }

    @DisplayName("Test case for adding an order by ID when order status is invalid")
    @Test
    void shouldReturnInvalidOrderStatusExceptionWhenAddingOrderWhichInvalidStatus() throws Exception {
        // Given
        Long orderId = 1L;

        Mockito.when(orderDbService.addOrder(orderId)).thenThrow(new InvalidOrderStatusException(OrderStatus.NEW));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("order.status.cannot.be.changed")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Status NEW for order cannot be changed.")));
    }

    @DisplayName("Test case for updating order")
    @Test
    void shouldUpdateOrder() throws Exception {
        UpdateOrderDto updateOrderDto = new UpdateOrderDto(OrderStatus.PROCESSING.name());

        Long orderId = 1L;
        OrderDto updatedOrderDto = new OrderDto(orderId, 2L, 3L, OrderStatus.PROCESSING.name(), LocalDateTime.of(2024, 9, 12, 12 ,0 ,0) , new BigDecimal("111.11"));

        Mockito.when(orderDbService.updateOrder(Mockito.anyLong(), Mockito.any())).thenReturn(updatedOrderDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updateOrderDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("PROCESSING")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Matchers.is("2024-09-12T12:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalAmount", Matchers.is(111.11)));
    }

    @DisplayName("Test case for updating order when order does not exist")
    @Test
    void shouldReturnGroupNotFoundExceptionWhenUpdateOrder() throws Exception {
        UpdateOrderDto updateOrderDto = new UpdateOrderDto(OrderStatus.PROCESSING.name());
        Long orderId = 1L;

        Mockito.when(orderDbService.updateOrder(Mockito.anyLong(), Mockito.any())).thenThrow(new OrderNotFoundException(orderId));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updateOrderDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is("ERROR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("order.does.not.exist")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Order with ID 1 not found.")));
    }

    @DisplayName("Test case for deleting existing order")
    @Test
    void shouldDeleteExistingOrder() throws Exception {
        // Given
        Long orderId = 1L;
        OrderDto orderDto = new OrderDto(orderId, 2L, 3L, "NEW", LocalDateTime.now(), new BigDecimal("100.00"));

        Mockito.when(orderDbService.deleteOrder(orderId)).thenReturn(true);
        Mockito.when(orderDbService.getOrderById(orderId)).thenReturn(orderDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @DisplayName("Test case for deleting non-existing order")
    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingOrder() throws Exception {
        // Given
        Long orderId = 1L;
        OrderDto orderDto = new OrderDto(orderId, 2L, 3L, "NEW", LocalDateTime.now(), new BigDecimal("100.00"));

        Mockito.when(orderDbService.deleteOrder(orderId)).thenReturn(false);
        Mockito.when(orderDbService.getOrderById(orderId)).thenReturn(orderDto);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldLogEventWhenOrderIsUpdated() throws Exception {
        // Given
        UpdateOrderDto updateOrderDto = new UpdateOrderDto(OrderStatus.PROCESSING.name());

        Long orderId = 1L;
        OrderDto updatedOrderDto = new OrderDto(orderId, 2L, 3L, OrderStatus.PROCESSING.name(), LocalDateTime.of(2024, 9, 12, 12 ,0 ,0) , new BigDecimal("111.11"));

        Mockito.when(orderDbService.updateOrder(Mockito.anyLong(), Mockito.any())).thenReturn(updatedOrderDto);

        Event event = new Event();
        Mockito.when(eventDbService.saveEvent(2L, EventTitle.ORDER_UPDATE)).thenReturn(event);
        Mockito.doNothing().when(eventDetailDbService).saveEventDetails(Mockito.any(), Mockito.anyMap());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updateOrderDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));

        Mockito.verify(eventDbService, Mockito.times(1)).saveEvent(2L, EventTitle.ORDER_UPDATE);
        Mockito.verify(eventDetailDbService, Mockito.times(1)).saveEventDetails(Mockito.eq(event), Mockito.anyMap());
    }

    @Test
    void shouldLogEventWhenOrderIsDeleted() throws Exception {
        // Given
        Long orderId = 1L;
        OrderDto orderDto = new OrderDto(orderId, 2L, 3L, "NEW", LocalDateTime.now(), new BigDecimal("100.00"));

        Mockito.when(orderDbService.deleteOrder(orderId)).thenReturn(true);
        Mockito.when(orderDbService.getOrderById(orderId)).thenReturn(orderDto);

        Event event = new Event();
        Mockito.when(eventDbService.saveEvent(2L, EventTitle.ORDER_DELETION)).thenReturn(event);
        Mockito.doNothing().when(eventDetailDbService).saveEventDetails(Mockito.any(), Mockito.anyMap());
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(eventDbService, Mockito.times(1)).saveEvent(2L, EventTitle.ORDER_DELETION);
        Mockito.verify(eventDetailDbService, Mockito.times(1)).saveEventDetails(Mockito.eq(event), Mockito.anyMap());
    }

    @Test
    void shouldLogEventWhenOrderIsAdded() throws Exception {
        // Given
        Long orderId = 1L;
        OrderDto addedOrderDto = new OrderDto(orderId, 2L, 3L, OrderStatus.NEW.name(), LocalDateTime.of(2024, 9, 12, 12 ,0 ,0) , new BigDecimal("111.11"));

        Mockito.when(orderDbService.addOrder(orderId)).thenReturn(addedOrderDto);

        Event event = new Event();
        Mockito.when(eventDbService.saveEvent(2L, EventTitle.ORDER_ADDITION)).thenReturn(event);
        Mockito.doNothing().when(eventDetailDbService).saveEventDetails(Mockito.any(), Mockito.anyMap());
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().is(200));

        Mockito.verify(eventDbService, Mockito.times(1)).saveEvent(2L, EventTitle.ORDER_ADDITION);
        Mockito.verify(eventDetailDbService, Mockito.times(1)).saveEventDetails(Mockito.eq(event), Mockito.anyMap());
    }
}

