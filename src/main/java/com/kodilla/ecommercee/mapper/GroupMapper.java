package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.controller.exception.ParentGroupNotFoundException;
import com.kodilla.ecommercee.domain.CreateGroupDto;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.UpdateGroupDto;
import com.kodilla.ecommercee.service.GroupDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMapper {

    private final GroupDbService groupDbService;
    private final ProductMapper productMapper;

    private List<Group> mapToGroupList(List<GroupDto> groupDtos) {
        return groupDtos.stream()
                .map(this::groupDtoToGroup)
                .toList();
    }

    public List<GroupDto> mapToGroupDtoList(final List<Group> groups) {
        return groups.stream()
                .map(this::groupToGroupDto)
                .toList();
    }

    public Group groupDtoToGroup(final GroupDto groupDto) {
        return new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDto.getParentGroupId() != null ? groupDbService.getGroup(groupDto.getParentGroupId()).orElse(null) : null,
                groupDto.getSubGroupIds().stream()
                        .map(groupDbService::getGroup)
                        .flatMap(Optional::stream)
                        .toList(),
                productMapper.mapToProductList(groupDto.getProducts()),
                groupDto.getCreatedDate()
        );
    }

    public GroupDto groupToGroupDto(final Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getParentGroup() != null ? group.getParentGroup().getId() : null,
                group.getSubGroups() != null ? group.getSubGroups().stream().map(Group::getId).toList() : new ArrayList<>(),
                group.getProducts() != null ? productMapper.mapToProductDtoList(group.getProducts()) : new ArrayList<>(),
                group.getCreatedDate()
        );
    }

    public Group createGroupDtoToGroup(CreateGroupDto createGroupDto) throws ParentGroupNotFoundException {
        return new Group(
                null,
                createGroupDto.getName(),
                createGroupDto.getParentGroupId() != null ? groupDbService.getGroup(createGroupDto.getParentGroupId()).orElseThrow(() -> new ParentGroupNotFoundException(createGroupDto.getParentGroupId())) : null,
                null,
                null,
                null
        );
    }

    public Group updateGroupDtoToGroup(Long groupId, UpdateGroupDto updateGroupDto) throws ParentGroupNotFoundException {
        return new Group(
                groupId,
                updateGroupDto.getName(),
                updateGroupDto.getParentGroupId() != null ? groupDbService.getGroup(updateGroupDto.getParentGroupId()).orElseThrow(() -> new ParentGroupNotFoundException(updateGroupDto.getParentGroupId())) : null,
                null,
                null,
                null
        );
    }
}
