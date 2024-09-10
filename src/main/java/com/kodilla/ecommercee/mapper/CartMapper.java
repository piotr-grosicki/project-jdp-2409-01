package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartDto mapCartToCartDto(Cart cart) {
        return new CartDto(
                cart.getCartId(),
                cart.getUser().getUserId()
        );
    }
}
