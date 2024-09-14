package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.EventDetail;
import com.kodilla.ecommercee.domain.EventDetailDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventDetailMapper {
    public List<EventDetailDto> mapEventDetailListToEventDetailDtoList(List<EventDetail> eventDEtails) {
        return eventDEtails.stream()
                .map(this::mapEventDetailToEventDetailDto)
                .toList();
    }

    public EventDetailDto mapEventDetailToEventDetailDto(EventDetail eventDetail) {
        return new EventDetailDto(
                eventDetail.getKey().toString(),
                eventDetail.getValue()
        );
    }
}
