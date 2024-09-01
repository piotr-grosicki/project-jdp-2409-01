package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.ItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/carts")
@Tag(name = "Carts", description = "Managing carts")
public class CartController {
    @Operation(
            description = "Creating an empty cart for user",
            summary = "Create a cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Empty cart created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User with ID {userId} does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class)
                    )
            )
    })
    @Parameter(
            name = "userId",
            in = ParameterIn.HEADER,
            required = true,
            description = "User identifier to create a cart for",
            schema = @Schema(type = "integer", example = "1")
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartDto> createCart(@RequestHeader("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CartDto(1L, userId)
        );
    }

    @Operation(
            description = "Receiving items from a cart by its cart ID",
            summary = "Get items"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Items retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cart with ID {cartId} does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class)
                    )
            )
    })
    @Parameter(
            name = "cartId",
            in = ParameterIn.PATH,
            required = true,
            description = "Cart identifier",
            schema = @Schema(type = "integer", example = "1")
    )
    @GetMapping(value = "/{cartId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ItemDto>> getItems(@PathVariable Long cartId) {
        return ResponseEntity.ok(new ArrayList<>(
                List.of(
                        new ItemDto(1L, 10L)
                )
        ));
    }

    @Operation(
            description = "Creating an item in cart",
            summary = "Create an item"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Item created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cart with ID {cartId} does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class)
                    )
            )
    })
    @PostMapping(value = "/{cartId}/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Item data to create.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemDto.class)
            )
    )
    public ResponseEntity<Void> createItem(@RequestBody ItemDto itemDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Deleting an item from cart",
            summary = "Delete an item")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Item deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cart with ID {cartId} or Product with ID {productId} does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class)
                    )
            )
    })
    @Parameter(
            name = "cartId",
            in = ParameterIn.PATH,
            required = true,
            description = "Cart identifier",
            schema = @Schema(type = "integer", example = "1")
    )
    @Parameter(
            name = "productId",
            in = ParameterIn.PATH,
            required = true,
            description = "Item (product) identifier",
            schema = @Schema(type = "integer", example = "1")
    )
    @DeleteMapping(value = "/{cartId}/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
