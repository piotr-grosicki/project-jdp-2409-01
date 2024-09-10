package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.UpdateGroupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/groups")
@Tag(name = "Groups", description = "Managing product groups")
public class GroupController {
    @Operation(
            description = "Retrieve all product groups",
            summary = "Get groups"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GroupDto>> getGroups() {
        return ResponseEntity.ok(new ArrayList<>(
                List.of(
                        new GroupDto(1L, "Group name", 0L)
                )
        ));
    }

    @Operation(
            description = "Creating a product group",
            summary = "Create a group"
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto) {
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
            @PathVariable Long groupId) {
        return ResponseEntity.ok(
                new GroupDto(1L, "Group name", 0L)
        );
    }

    @Operation(
            description = "Updating a product group",
            summary = "Update a group")
    @PutMapping(value = "/{groupId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateGroup(
            @Parameter(description = "Group identifier", required = true, example = "1")
            @PathVariable Long groupId,
            @RequestBody UpdateGroupDto updateGroupDto) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
