package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateGroupDto {
    @Schema(description = "Name of the group", example = "Group Name")
    private String name;
    @Schema(description = "Identifier of the parent group", example = "0")
    private Long parentGroupId;
}