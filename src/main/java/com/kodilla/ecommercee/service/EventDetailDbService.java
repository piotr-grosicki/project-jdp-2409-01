package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Event;
import com.kodilla.ecommercee.domain.EventDetail;
import com.kodilla.ecommercee.domain.EventDetailKey;
import com.kodilla.ecommercee.repository.EventDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventDetailDbService {
    private final EventDetailRepository eventDetailRepository;

    public void saveEventDetails(Event event, Map<EventDetailKey, String> eventDetails) {
        eventDetails.entrySet().stream()
                        .forEach(e -> {
                            EventDetail eventDetail = new EventDetail();
                            eventDetail.setEvent(event);
                            eventDetail.setKey(e.getKey());
                            eventDetail.setValue(e.getValue());
                            eventDetailRepository.save(eventDetail);
                        });
    }
}
