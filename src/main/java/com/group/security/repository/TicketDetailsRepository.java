package com.group.security.repository;
import com.group.security.Model.TicketDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketDetailsRepository extends JpaRepository<TicketDetails, Integer> {
    List<TicketDetails> findByEventId(Long eventId);

    @Query("SELECT new com.group.security.Model.TicketDetails(td.id, td.eventId, td.ticketType, td.ticketPrice) " +
            "FROM TicketDetails td WHERE td.eventId = :eventId")
    List<TicketDetails> findCustomTicketDetailsByEventId(@Param("eventId") Long eventId);
}