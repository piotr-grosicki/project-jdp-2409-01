package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.GroupNotFoundException;
import com.kodilla.ecommercee.controller.exception.ParentGroupNotFoundException;
import com.kodilla.ecommercee.domain.CreateGroupDto;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.UpdateGroupDto;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.GroupDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/groups")
@Tag(name = "Groups", description = "Managing product groups")
public class GroupController {
    private final GroupMapper groupMapper;
    private final GroupDbService groupDbService;

    @Operation(
            description = "Retrieve all product groups",
            summary = "Get groups"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GroupDto>> getGroups() {
        List<Group> groups = groupDbService.getAllGroups();
        return ResponseEntity.ok(groupMapper.mapToGroupDtoList(groups));
    }

    @Operation(
            description = "Creating a product group",
            summary = "Create a group"
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createGroup(@RequestBody CreateGroupDto createGroupDto) throws ParentGroupNotFoundException {
        Group group = groupMapper.createGroupDtoToGroup(createGroupDto);
        groupDbService.saveGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Receiving a product group by its group ID",
            summary = "Get a group"
    )
    @GetMapping(value = "/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GroupDto> getGroup(
            @Parameter(description = "Group identifier", required = true, example = "1")
            @PathVariable Long groupId) throws GroupNotFoundException {
        if (!groupDbService.getGroup(groupId).isPresent()) {
            throw new GroupNotFoundException(groupId);
        } else {
            return ResponseEntity.ok(groupMapper.groupToGroupDto(groupDbService.getGroup(groupId).get()));
        }
    }

    @Operation(
            description = "Updating a product group",
            summary = "Update a group")
    @PutMapping(value = "/{groupId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GroupDto> updateGroup(
            @PathVariable Long groupId,
            @Parameter(description = "Group identifier", required = true, example = "1")
            @RequestBody UpdateGroupDto updateGroupDto) throws GroupNotFoundException, ParentGroupNotFoundException {
        if (!groupDbService.getGroup(groupId).isPresent()) {
            throw new GroupNotFoundException(groupId);
        } else {
            Group group = groupMapper.updateGroupDtoToGroup(groupId, updateGroupDto);

            return ResponseEntity.ok(groupMapper.groupToGroupDto(groupDbService.updateGroup(group)));
        }
    }
}
