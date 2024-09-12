package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDbService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product getProduct(final Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean deleteProduct(final Long productId) throws ProductNotFoundException {
        if (productRepository.existsById(productId)) {
            Product product = productRepository.findById(productId).get();

            List<Cart> carts = cartRepository.findByCartProductsContains(product);

            for (Cart cart : carts) {
                cart.getCartProducts().remove(product);
                cartRepository.save(cart);
            }

            productRepository.deleteById(productId);
            return true;
        } else {
            return false;
        }
    }
}
