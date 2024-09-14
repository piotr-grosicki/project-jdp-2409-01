package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "EVENTS_DETAILS")
public class EventDetail {
    @Id
    @GeneratedValue
    @Column(name = "EVENT_DETAIL_ID", unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "EVENT_ID", nullable = false)
    private Event event;
    @NotNull
    @Column(name = "DETAIL_KEY")
    @Enumerated(EnumType.STRING)
    private EventDetailKey key;
    @NotNull
    @Column(name = "DETAIL_VALUE")
    private String value;
}

