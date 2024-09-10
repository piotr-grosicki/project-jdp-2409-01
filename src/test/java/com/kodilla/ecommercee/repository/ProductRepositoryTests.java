package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@DisplayName("Tests for ProductRepository")
@SpringBootTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;

    @DisplayName("Test case for getAll method")
    @Test
    public void getAll() {
        //Given
        Product product = new Product(null, "Toyota", "Car", new BigDecimal(55000), 1, null, LocalDate.now());
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();
        Product product2 = new Product(null, "Yamaha", "Motorcycle", new BigDecimal(16000), 1, null, LocalDate.now());
        Product savedProduct2 = productRepository.save(product2);
        Long productId2 = savedProduct2.getId();
        //When
        Iterable<Product> products = productRepository.findAll();
        List<Product> listOfProducts = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toList());
        listOfProducts.sort(Comparator.comparing(Product::getId));
        //Then
        Assertions.assertEquals(2, listOfProducts.size());
        //Clean Up
        productRepository.deleteById(productId);
        productRepository.deleteById(productId2);
    }

    @DisplayName("Test case for save method")
    @Test
    public void shouldSaveProduct() {
        //Given
        Product product = new Product(null, "Toyota", "Car", new BigDecimal(55000), 1, null, LocalDate.now());
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();
        //When
        Optional<Product> productSaved = productRepository.findById(productId);
        //Then
        Assertions.assertTrue(productSaved.isPresent());
        Assertions.assertEquals("Toyota", productSaved.get().getName());
        //Clean Up
        productRepository.deleteById(productId);
    }
    @DisplayName("Test case for delete method")
    @Test
    void shouldDeleteProductButNotGroup() {
        //Given
        Group group = new Group(null,"Small SUV", null, null, null, LocalDateTime.now());
        Group savedGroup = groupRepository.save(group);
        Long groupId = savedGroup.getId();
        Product product = new Product(null, "Toyota", "Car", new BigDecimal(55000), 1, group, LocalDate.now());
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();
        //When
        Optional<Group> groupSaved = groupRepository.findById(groupId);
        productRepository.deleteById(productId);
        //Then
        Assertions.assertFalse(productRepository.findById(productId).isPresent());
        Assertions.assertTrue(groupSaved.isPresent());
        Assertions.assertEquals("Small SUV", groupSaved.get().getName());
        //Clean up
        groupRepository.deleteById(groupId);
    }

}
