package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue
    @Column(name = "EVENT_ID", unique = true)
    private Long id;
    @NotNull
    @Column(name = "USER_ID")
    private Long userId;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TITLE")
    private EventTitle eventTitle;
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<EventDetail> eventDetails;
    @NotNull
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;
}

