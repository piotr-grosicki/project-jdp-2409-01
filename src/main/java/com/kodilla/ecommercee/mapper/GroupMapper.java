package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.controller.GroupNotFoundException;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.service.GroupDbService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMapper {

    private GroupDbService groupDbService;

    public List<GroupDto> mapToGroupDtoList(final List<Group> groups) {
        return groups.stream()
                .map(group -> {
                    try {
                        return groupToGroupDto(group);
                    } catch (GroupNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public Group groupDtoToGroup(final GroupDto groupDto) throws GroupNotFoundException {
        return new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDbService.getGroup(groupDto.getParentGroupId()),
                groupDto.getSubGroups(),
                groupDto.getProducts(),
                groupDto.getCreatedDate()
        );
    }

    public GroupDto groupToGroupDto(final Group group) throws GroupNotFoundException {
        return new GroupDto(
                group.getId(),
                group.getName(),
                groupDbService.getGroup(group.getParentGroup().getId()).getId(),
                group.getSubGroups(),
                group.getProducts(),
                group.getCreatedDate()
        );
    }

}
