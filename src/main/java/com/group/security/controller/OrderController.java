package com.group.security.controller;

import com.google.zxing.WriterException;
import com.group.security.OrderDTO.OrderRequest;
import com.group.security.entity.Order;
import com.group.security.service.EmailService;
import com.group.security.service.OrderService;
import com.group.security.utils.QRCodeGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final EmailService emailService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
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
    public ResponseEntity<String> generateQR(@PathVariable("orderId") Long orderId) throws IOException, WriterException, MessagingException, jakarta.mail.MessagingException {
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
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        try {
            // Additional validation logic if needed
            Order order = orderService.createOrder(
                    orderRequest.getOrderId(),
                    orderRequest.getUserId(),
                    orderRequest.getEventId(),
                    orderRequest.getAmount(),
                    orderRequest.getPaymentStatus()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Order created successfully: Payment Status: " + order.getPaymentStatus() + ", Timestamp: " + order.getTimestamp());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage().equals("User not found") || e.getMessage().equals("Event not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: " + e.getMessage());
            } else {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error: An internal server error occurred while creating the order.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: An internal server error occurred while creating the order.");
        }
    }
}
