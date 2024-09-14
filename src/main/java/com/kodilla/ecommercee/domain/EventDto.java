package com.kodilla.ecommercee.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class EventDto {
    @Schema(description = "Unique identifier of the event", example = "1")
    private Long id;
    @Schema(description = "User identifier associated with the event", example = "1")
    private Long userId;
    @Schema(description = "Title of the event", example = "USER_CREATED")
    private EventTitle eventTitle;
    @Schema(description = "Details related to the event, stored as key-value pairs",
            example = "[{\"detailKey\": \"USER_ID\", \"detailValue\": \"1234\"}]")
    private List<EventDetailDto> eventDetails;
    @Schema(description = "Event creation date", example = "2024-09-13T12:00:00")
    private LocalDateTime creationDate;
}
