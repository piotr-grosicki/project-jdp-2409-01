package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMapper {
    public List<GroupDto> mapToGroupDtoList(final List<Group> groups) {
        return groups.stream()
                .map(this::groupToGroupDto)
                .toList();
    }

    public Group groupDtoToGroup(final GroupDto groupDto) {
        return new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDto.getParentGroup(),
                groupDto.getSubGroups(),
                groupDto.getProducts(),
                groupDto.getCreatedDate()
        );
    }

    public GroupDto groupToGroupDto(final Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getParentGroup(),
                group.getSubGroups(),
                group.getProducts(),
                group.getCreatedDate()
        );
    }

}
