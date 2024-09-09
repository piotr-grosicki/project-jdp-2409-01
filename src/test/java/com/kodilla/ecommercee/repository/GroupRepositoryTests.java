package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DisplayName("Tests for GroupRepository")
@SpringBootTest
public class GroupRepositoryTests {

    @Autowired
    private GroupRepository groupRepository;

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

    @DisplayName("Test case for deleting parent group")
    @Test
    void shouldDeleteGroupWhenParentGroupIsDeleted() {
        // Given
        Group parentGroup = new Group(null, "Parent group", null, null, null, LocalDateTime.now());
        Group childGroup = new Group(null, "Child group", parentGroup, null, null, LocalDateTime.now());

        groupRepository.save(parentGroup);
        groupRepository.save(childGroup);

        Long parentGroupId = parentGroup.getId();
        Long childGroupId = childGroup.getId();
        // When
        groupRepository.deleteById(parentGroupId);
        // Then
        Optional<Group> retrievedParentGroup = groupRepository.findById(parentGroupId);
        Optional<Group> retrievedChildGroup = groupRepository.findById(childGroupId);
        Assertions.assertFalse(retrievedParentGroup.isPresent());
        Assertions.assertFalse(retrievedChildGroup.isPresent());
    }
}
