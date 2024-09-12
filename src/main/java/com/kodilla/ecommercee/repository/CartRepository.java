package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    @Override
    Cart save(Cart cart);
    @Override
    Optional<Cart> findById(Long id);
    List<Cart> findByCartProductsContains(Product product);
}
