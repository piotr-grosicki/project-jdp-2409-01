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
@RequestMapping("/api/groups")
@Tag(name = "Groups", description = "Managing product groups")
public class GroupController {
    @Operation(
            description = "Retrieve all product groups",
            summary = "Get groups"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product groups retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GroupDto.class))
                    )
            )
    })
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product group created successfully"
            )
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Group data to create.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GroupDto.class)
            )
    )
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            description = "Receiving a product group by its group ID",
            summary = "Get a group"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product group retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Group with ID {groupId} does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class)
                    )
            )
    })
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
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Group updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Group with ID {groupId} does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Exception.class)
                    )
            )
    })
    @PutMapping(value = "/{groupId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Group data to update.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateGroupDto.class)
            )
    )
    public ResponseEntity<Void> updateGroup(
            @Parameter(description = "Group identifier", required = true, example = "1")
            @PathVariable Long groupId,
            @RequestBody UpdateGroupDto updateGroupDto) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
