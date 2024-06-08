package com.group.security.repository;

import com.group.security.Model.Eventcards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventCardsRepository extends JpaRepository<Eventcards, Long> {
    Optional<Eventcards> findByEventId(Long eventId);
}
