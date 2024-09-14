package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DisplayName("Tests for GroupRepository")
@SpringBootTest
public class GroupRepositoryTests {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("Test case for save method")
    @Test
    void shouldSaveGroup() throws InterruptedException {
        // Given
        Group group = new Group(null,"Group name", null, null, null, LocalDateTime.now());
        Group savedGroup = groupRepository.save(group);
        Long savedGroupId = savedGroup.getId();
        // When
        Optional<Group> retrievedGroup = groupRepository.findById(savedGroupId);
        // Then
        Assertions.assertTrue(retrievedGroup.isPresent());
        Assertions.assertEquals("Group name", retrievedGroup.get().getName());
        // CleanUp
        groupRepository.deleteById(savedGroupId);
    }

    @DisplayName("Test case for deleting parent group: should return validation constraint violation when deleting parent group having child groups")
    @Test
    void shouldReturnValidationConstraintViolationWhenDeletingParentGroup() {
        // Given
        Group parentGroup = new Group(null, "Parent group", null, null, null, LocalDateTime.now());
        Group savedParentGroup = groupRepository.save(parentGroup);
        Long parentGroupId = savedParentGroup.getId();

        Group childGroup = new Group(null, "Child group", parentGroup, null, null, LocalDateTime.now());
        Group savedChildGroup = groupRepository.save(childGroup);
        Long childGroupId = savedChildGroup.getId();
        // When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> groupRepository.deleteById(parentGroupId));

        Assertions.assertTrue(groupRepository.findById(parentGroupId).isPresent());
        // CleanUp
        groupRepository.deleteById(childGroupId);
        groupRepository.deleteById(parentGroupId);
    }

    @DisplayName("Test case for deleting group: should return validation constraint violation when deleting group having products")
    @Test
    public void shouldReturnValidationConstraintViolationWhenDeletingGroupWithProducts() {
        // Given
        Group group = new Group(null, "Parent group", null, null, null, LocalDateTime.now());
        Group savedGroup = groupRepository.save(group);
        Long groupId = savedGroup.getId();

        Product product = new Product(null, "Product name", "Product description", new BigDecimal(111.11), 1,  group, LocalDate.now());
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();
        // When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> groupRepository.deleteById(groupId));

        Assertions.assertTrue(productRepository.findById(productId).isPresent());
        // CleanUp
        productRepository.deleteById(productId);
        groupRepository.deleteById(groupId);
    }
}
