package com.group.security.service;

import com.group.security.Model.Eventcards;
import com.group.security.entity.Order;
import com.group.security.entity.UserInfo;
import com.group.security.repository.EventCardsRepository;
import com.group.security.repository.OrderRepository;
import com.group.security.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserInfoRepository userInfoRepository;
    @Autowired
    private EventCardsRepository eventCardsRepository;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public UserInfo findUserInfoById(Integer userId) {
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Order createOrder(Long orderId, Long userId, Long eventId, BigDecimal amount, String paymentStatus) {
        if (orderId == null || userId == null || eventId == null || amount == null || paymentStatus == null) {
            throw new IllegalArgumentException("Order parameters cannot be null");
        }

        UserInfo userInfo = userInfoRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
        Eventcards eventCards = eventCardsRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Order order = new Order();
        order.setOrderId(orderId);
        order.setTimestamp(new Timestamp(System.currentTimeMillis()));
        order.setUserInfo(userInfo);
        order.setEventCards(eventCards);
        order.setAmount(amount);
        order.setPaymentStatus(paymentStatus);

        return orderRepository.save(order);
    }
}
