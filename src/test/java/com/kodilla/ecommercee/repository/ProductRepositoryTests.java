package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@DisplayName("Tests for ProductRepository")
@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;

    @DisplayName("Test case for getAll method")
    @Test
    public void getAll() {
        //Given
        Group group = new Group(null,"Small SUV", null, null, null, LocalDateTime.now());
        Group savedGroup = groupRepository.save(group);
        Product product = new Product(1L, "Toyota", "Car", new BigDecimal(55000), 1, group, LocalDate.now());
        Product savedProduct = productRepository.save(product);
        Group group2 = new Group(null,"Motorcycles", null, null, null, LocalDateTime.now());
        Group savedGroup2 = groupRepository.save(group2);
        Product product2 = new Product(2L, "Yamaha", "Motorcycle", new BigDecimal(16000), 1, group2, LocalDate.now());
        Product savedProduct2 = productRepository.save(product2);
        //When
        Iterable<Product> products = productRepository.findAll();
        List<Product> listOfProducts = StreamSupport.stream(products.spliterator(), false).collect(Collectors.toList());
        //Then
        Assertions.assertEquals(2, listOfProducts.size());
        Assertions.assertEquals("Yamaha", listOfProducts.get(1).getName());
    }

    @DisplayName("Test case for save method")
    @Test
    public void shouldSaveProduct() {
        //Given
        Group group = new Group(null,"Small SUV", null, null, null, LocalDateTime.now());
        Group savedGroup = groupRepository.save(group);
        Product product = new Product(1L, "Toyota", "Car", new BigDecimal(55000), 1, group, LocalDate.now());
        Product savedProduct = productRepository.save(product);
        //When
        Optional<Product> productSaved = productRepository.findById(savedProduct.getId());
        //Then
        Assertions.assertTrue(productSaved.isPresent());
        Assertions.assertEquals("Toyota", productSaved.get().getName());

    }
    @DisplayName("Test case for delete method")
    @Test
    void shouldDeleteProductButNotGroup() {
        //Given
        Group group = new Group(null,"Small SUV", null, null, null, LocalDateTime.now());
        Group savedGroup = groupRepository.save(group);
        Product product = new Product(1L, "Toyota", "Car", new BigDecimal(55000), 1, group, LocalDate.now());
        Product savedProduct = productRepository.save(product);
        //When
        Optional<Group> groupSaved = groupRepository.findById(savedGroup.getId());
        productRepository.delete(savedProduct);
        //Then
        Assertions.assertFalse(productRepository.findById(savedProduct.getId()).isPresent());
        Assertions.assertTrue(groupSaved.isPresent());
        Assertions.assertEquals("Small SUV", groupSaved.get().getName());
    }

}
