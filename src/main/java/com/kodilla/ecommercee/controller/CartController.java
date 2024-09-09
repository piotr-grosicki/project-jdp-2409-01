package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.ItemMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.CartDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/carts")
@Tag(name = "Carts", description = "Managing carts")
public class CartController {
    private final CartDbService cartDbService;
    private final CartMapper cartMapper;
    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;

    @Operation(
            description = "Creating an empty cart for user",
            summary = "Create a cart"
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartDto> createCart(@RequestHeader("userId") Long userId) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.mapCartToCartDto(cartDbService.createCart(userId)));
    }

    @Operation(
            description = "Receiving items from a cart by its cart ID",
            summary = "Get items"
    )
    @GetMapping(value = "/{cartId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ItemDto>> getItems(@PathVariable Long cartId) throws CartNotFoundException {
        return ResponseEntity.ok(itemMapper.mapProductListToItemDtoList(cartDbService.getCartProducts(cartId)));
    }

    @Operation(
            description = "Creating an item in cart",
            summary = "Create an item"
    )
    @PostMapping(value = "/{cartId}/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createItem(
            @PathVariable Long cartId,
            @RequestBody ItemDto itemDto) throws CartNotFoundException, ProductNotFoundException, QuantityLessThanZeroException, QuantityLessThanZeroException, InsufficientStockException {
                cartDbService.createCartProducts(cartId, itemDto.getProductId(), itemDto.getProductQuantity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Deleting an item from cart",
            summary = "Delete an item")
    @DeleteMapping(value = "/{cartId}/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long cartId,
            @PathVariable Long productId) throws CartNotFoundException, ProductNotFoundException {
        cartDbService.deleteCartProduct(cartId, productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            description = "Creating an order for cart",
            summary = "Create an order"
    )
    @PostMapping(value = "/{cartId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long cartId) throws CartNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.mapOrderToOrderDto(cartDbService.createCartOrder(cartId)));
    }
}
