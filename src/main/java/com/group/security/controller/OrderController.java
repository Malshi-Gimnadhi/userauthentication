package com.group.security.controller;

import com.google.zxing.WriterException;
import com.group.security.OrderDTO.OrderRequest;
import com.group.security.entity.Order;
import com.group.security.service.EmailService;
import com.group.security.service.OrderService;
import com.group.security.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final EmailService emailService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable("id") Long id) {
        Order order = orderService.findById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/generateQR/{orderId}")
    public ResponseEntity<String> generateQR(@PathVariable("orderId") Long orderId) throws IOException, WriterException, MessagingException, WriterException, jakarta.mail.MessagingException {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        String qrCodePath = QRCodeGenerator.generateQRCode(order);
        emailService.sendEmailWithAttachment(
                order.getUserInfo().getEmail(), // Email from UserInfo
                "EasyTicket.LK | Your Event E-Ticket",
                "Attached: E-Ticket (QR Code) for Your Order—Click, Book and Enjoy!",
                new File(qrCodePath)
        );

        return ResponseEntity.ok("QR code generated and email sent successfully.");
    }

    @PostMapping("/add")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(
                orderRequest.getUserId(),
                orderRequest.getEventId(),
                orderRequest.getAmount(),
                orderRequest.getPaymentStatus()
        );
        return ResponseEntity.ok(order);
    }

}
