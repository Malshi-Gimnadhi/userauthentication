package com.group.security.entity;

import com.group.security.Model.Eventcards;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    private Long orderId;

    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "eventId")
    private Eventcards eventCards;

    private BigDecimal amount;
    private String paymentStatus;

}
