package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
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
@RequestMapping("/api/groups")
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
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto) throws GroupNotFoundException {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        groupDbService.saveGroup(group);
        return ResponseEntity.ok().build();
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
        return ResponseEntity.ok(groupMapper.groupToGroupDto(groupDbService.getGroup(groupId)));
    }

    @Operation(
            description = "Updating a product group",
            summary = "Update a group")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GroupDto> updateGroup(
            @Parameter(description = "Group identifier", required = true, example = "1")
            @RequestBody GroupDto groupDto) throws GroupNotFoundException {
        Group group = groupMapper.groupDtoToGroup(groupDto);
        return ResponseEntity.ok(groupMapper.groupToGroupDto(group));
    }
}
