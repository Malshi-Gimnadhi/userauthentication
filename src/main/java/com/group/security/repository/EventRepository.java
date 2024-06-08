package com.group.security.repository;

import com.group.security.Model.Eventcards;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Eventcards, Long> {
    List<Eventcards> findByEventCategory(String eventCategory);
}
