package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Override
    Event save(Event event);
    List<Event> getEventByUserId(Long userId);
}
