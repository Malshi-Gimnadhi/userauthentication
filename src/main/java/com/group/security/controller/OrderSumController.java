package com.group.security.controller;

import com.group.security.OrderDTO.OrderInfoDTO;
import com.group.security.entity.OrderSumEntity;
import com.group.security.service.OrderInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class OrderSumController {
    private final OrderInfoService orderInfoService;

    public OrderSumController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }


    @GetMapping("/order-summary")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrderInfoDTO> getAllUsers() {
        return orderInfoService.getAllUsers();
    }

    @GetMapping("/order-summary/{id}")
//    @PreAuthorize("hasAuthority('USER')")
    public OrderInfoDTO getOrderById(@PathVariable Long id) {
        return orderInfoService.getOrderById(String.valueOf(id));

    }

    @PostMapping("/order-summary")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OrderSumEntity> createOrder(@RequestBody OrderSumEntity order) {
        OrderSumEntity createdOrder = orderInfoService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OrderSumEntity> editOrder(
            @PathVariable String id,
            @RequestBody OrderSumEntity updatedOrder) {
        OrderSumEntity editedOrder = orderInfoService.editOrder(id, updatedOrder);
        return ResponseEntity.ok(editedOrder);
    }

//    @PostMapping("/payment/notify")
//    public ResponseEntity<String> paymentNotify(@RequestBody Map<String, String> payload) {
//        String transactionId = payload.get("transactionId");
//        String status = payload.get("status");
//        orderInfoService.updatePaymentStatus(transactionId, status);
//        return ResponseEntity.ok("Payment status updated");
//    }
}
