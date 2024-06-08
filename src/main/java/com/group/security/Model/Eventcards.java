package com.group.security.Model;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Eventcards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private Long eventId;

    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String eventDescription;
    private String ticketDetails;
    private String eventCategory;
    private String flyerLink;

    private String organizerName;
    private int organizerPhone;

    private String organizerNic;
    private String organizerEmail;


    @OneToMany(mappedBy = "eventcards", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketDetails> ticketDetailsList;

    public Eventcards() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getTicketDetails() {
        return ticketDetails;
    }

    public void setTicketDetails(String ticketDetails) {
        this.ticketDetails = ticketDetails;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getFlyerLink() {
        return flyerLink;
    }

    public void setFlyerLink(String flyerLink) {
        this.flyerLink = flyerLink;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public int getOrganizerPhone() {
        return organizerPhone;
    }

    public void setOrganizerPhone(int organizerPhone) {
        this.organizerPhone = organizerPhone;
    }

    public String getOrganizerNic() {
        return organizerNic;
    }

    public void setOrganizerNic(String organizerNic) {
        this.organizerNic = organizerNic;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }
}



