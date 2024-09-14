package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Event;
import com.kodilla.ecommercee.domain.EventDetail;
import com.kodilla.ecommercee.domain.EventDetailDto;
import com.kodilla.ecommercee.domain.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final EventDetailMapper eventDetailMapper;

    public List<EventDto> mapEventListToEventDtoList(List<Event> events) {
        return events.stream()
                .map(this::mapEventToEventDto)
                .toList();
    }

    public EventDto mapEventToEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getUserId(),
                event.getEventTitle(),
                eventDetailMapper.mapEventDetailListToEventDetailDtoList(event.getEventDetails()),
                event.getCreationDate()
        );
    }
}
