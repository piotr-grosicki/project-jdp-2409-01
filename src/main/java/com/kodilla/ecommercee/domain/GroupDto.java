package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GroupDto {
    @Schema(description = "Unique identifier of the group", example = "1")
    private Long id;
    @Schema(description = "Name of the group", example = "Group Name")
    private String name;
    @Schema(description = "Identifier of the parent group", example = "0")
    private Long parentGroupId;
}