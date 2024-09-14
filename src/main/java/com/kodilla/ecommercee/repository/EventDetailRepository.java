package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.EventDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailRepository extends CrudRepository<EventDetail, Long> {
    @Override
    EventDetail save(EventDetail eventDetail);
}
