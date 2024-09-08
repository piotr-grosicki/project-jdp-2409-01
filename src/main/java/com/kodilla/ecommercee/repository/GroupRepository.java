package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    @Override
    @NonNull
    List<Group> findAll();
}
