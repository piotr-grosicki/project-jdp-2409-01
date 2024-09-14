package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.domain.Event;
import com.kodilla.ecommercee.domain.EventDto;
import com.kodilla.ecommercee.mapper.EventMapper;
import com.kodilla.ecommercee.service.EventDbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/history")
@Tag(name = "History", description = "Managing user operation history")
public class HistoryController {
    private final EventDbService eventDbService;
    private final EventMapper eventMapper;

    @Operation(
            description = "Fetches a list of all operations performed by the user",
            summary = "Retrieve user operation history"
    )
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EventDto>> getUserOperationHistory(@PathVariable Long userId) throws UserNotFoundException {
        List<Event> events = eventDbService.getAllEvents(userId);

        return ResponseEntity.ok(eventMapper.mapEventListToEventDtoList(events));
    }
}
