package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.*;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CartDbService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public Cart createCart(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return cartRepository.save(
                new Cart(
                        null,
                        user,
                        new ArrayList<>(),
                        LocalDateTime.now())
        );
    }

    public List<Product> getCartProducts(Long cartId) throws CartNotFoundException {
        Cart cart = getCart(cartId);

        return cart.getCartProducts();
    }

    public void createCartProducts(Long cartId, Long productId, Long quantity) throws CartNotFoundException, ProductNotFoundException, InsufficientStockException, QuantityLessThanZeroException {
        if (quantity < 0) {
            throw new QuantityLessThanZeroException();
        }

        Cart cart = getCart(cartId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException(productId);
        }

        List<Product> productsToAdd = Stream.generate(() -> product)
                .limit(quantity)
                .toList();

        product.setQuantity((int)(product.getQuantity() - quantity));

        cart.getCartProducts().addAll(productsToAdd);

        productRepository.save(product);

        cartRepository.save(cart);
    }

    public void deleteCartProduct(Long cartId, Long productId) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = getCart(cartId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        boolean removed = cart.getCartProducts().remove(product);

        if (!removed) {
            throw new ProductNotFoundException(productId);
        }

        cartRepository.save(cart);
    }

    public Order createCartOrder(Long cartId) throws CartNotFoundException {
        Cart cart = getCart(cartId);

        BigDecimal totalAmount = cart.getCartProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order(
                null,
                cart.getUser(),
                totalAmount,
                LocalDateTime.now(),
                OrderStatus.CREATED,
                cart
        );

        return orderRepository.save(order);
    }

    public Cart getCart(Long cartId) throws CartNotFoundException {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));
    }
}
