package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.*;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.ItemMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.CartDbService;
import com.kodilla.ecommercee.service.EventDbService;
import com.kodilla.ecommercee.service.EventDetailDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/carts")
@Tag(name = "Carts", description = "Managing carts")
public class CartController {
    private final CartDbService cartDbService;
    private final CartMapper cartMapper;
    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;
    private final EventDbService eventDbService;
    private final EventDetailDbService eventDetailDbService;

    @Operation(
            description = "Creates a new, empty shopping cart for a specified user identified by its ID",
            summary = "Create an empty cart"
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartDto> createCart(@RequestHeader("userId") Long userId) throws UserNotFoundException {
        CartDto createdCartDto = cartMapper.mapCartToCartDto(cartDbService.createCart(userId));

        Event event = eventDbService.saveEvent(createdCartDto.getUserId(), EventTitle.CART_CREATION);
        Map<EventDetailKey, String> eventDetails = new HashMap<>();
        eventDetails.put(EventDetailKey.CART_ID, createdCartDto.getId().toString());
        eventDetailDbService.saveEventDetails(event, eventDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCartDto);
    }

    @Operation(
            description = "Retrieves a list of all items present in a specific cart, identified by its cart ID",
            summary = "Retrieve all items from the cart"
    )
    @GetMapping(value = "/{cartId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ItemDto>> getItems(@PathVariable Long cartId) throws CartNotFoundException {
        return ResponseEntity.ok(itemMapper.mapProductListToItemDtoList(cartDbService.getCartProducts(cartId)));
    }

    @Operation(
            description = "Adds a specific product to the cart. The cart ID and product details (quantity and productId) are provided in the request.",
            summary = "Add an item to the cart"
    )
    @PostMapping(value = "/{cartId}/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createItem(
            @PathVariable Long cartId,
            @RequestBody ItemDto itemDto) throws CartNotFoundException, ProductNotFoundException, QuantityLessThanZeroException, InsufficientStockException {
        cartDbService.createCartProducts(cartId, itemDto.getProductId(), itemDto.getProductQuantity());

        Event event = eventDbService.saveEvent(cartDbService.getCart(cartId).getUser().getUserId(), EventTitle.CART_ITEM_ADDITION);
        Map<EventDetailKey, String> eventDetails = new HashMap<>();
        eventDetails.put(EventDetailKey.CART_ID, cartId.toString());
        eventDetails.put(EventDetailKey.PRODUCT_ID, itemDto.getProductId().toString());
        eventDetails.put(EventDetailKey.PRODUCT_QUANTITY, itemDto.getProductQuantity().toString());
        eventDetailDbService.saveEventDetails(event, eventDetails);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Removes a specific product from the cart based on the cart ID and product ID",
            summary = "Delete an item from the cart")
    @DeleteMapping(value = "/{cartId}/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long cartId,
            @PathVariable Long productId) throws CartNotFoundException, ProductNotFoundException {
        cartDbService.deleteCartProduct(cartId, productId);

        Event event = eventDbService.saveEvent(cartDbService.getCart(cartId).getUser().getUserId(), EventTitle.CART_ITEM_REMOVAL);
        Map<EventDetailKey, String> eventDetails = new HashMap<>();
        eventDetails.put(EventDetailKey.CART_ID, cartId.toString());
        eventDetails.put(EventDetailKey.PRODUCT_ID, productId.toString());
        eventDetailDbService.saveEventDetails(event, eventDetails);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            description = "Creates an order based on the items present in the specified cart",
            summary = "Create an order from the cart"
    )
    @PostMapping(value = "/{cartId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long cartId) throws CartNotFoundException {
        OrderDto createdOrderDto = orderMapper.mapOrderToOrderDto(cartDbService.createCartOrder(cartId));

        Event event = eventDbService.saveEvent(createdOrderDto.getUserId(), EventTitle.ORDER_CREATION_FROM_CART);
        Map<EventDetailKey, String> eventDetails = new HashMap<>();
        eventDetails.put(EventDetailKey.ORDER_ID, createdOrderDto.getId().toString());
        eventDetails.put(EventDetailKey.CART_ID, createdOrderDto.getCartId().toString());
        eventDetailDbService.saveEventDetails(event, eventDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDto);
    }
}
