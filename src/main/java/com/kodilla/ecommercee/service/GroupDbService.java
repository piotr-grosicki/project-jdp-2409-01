package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.ParentGroupNotFoundException;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupDbService {
    private final GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group saveGroup(Group group) {
        group.setCreatedDate(LocalDateTime.now());

        return groupRepository.save(group);
    }

    public Group updateGroup(Group group) {
        group.setCreatedDate(groupRepository.findById(group.getId()).get().getCreatedDate());

        return groupRepository.save(group);
    }

    public Optional<Group> getGroup(final Long groupId) {
        return groupRepository.findById(groupId);
    }
}
