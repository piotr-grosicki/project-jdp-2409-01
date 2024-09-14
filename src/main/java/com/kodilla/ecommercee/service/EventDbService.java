package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.domain.Event;
import com.kodilla.ecommercee.domain.EventTitle;
import com.kodilla.ecommercee.repository.EventRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventDbService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<Event> getAllEvents(Long userId) throws UserNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return eventRepository.getEventByUserId(userId);
    }

    public Event saveEvent(Long userId, EventTitle eventTitle) {
        Event event = new Event();
        event.setUserId(userId);
        event.setEventTitle(eventTitle);
        event.setCreationDate(LocalDateTime.now());

        return eventRepository.save(event);
    }
}
