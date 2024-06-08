package com.group.security.service;

import com.group.security.OrderDTO.OrderInfoDTO;
import com.group.security.entity.OrderSumEntity;
import com.group.security.repository.OrderSumRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service

public class OrderInfoService {
    private final OrderSumRepository orderSumRepository;

    @Value("${merchant.secret}")
    private String merchantSecretPay;

    @Value("${merchant.id}")
    private String merchantId;

    public OrderInfoService(OrderSumRepository orderSumRepository) {
        this.orderSumRepository = orderSumRepository;
    }

    public List<OrderInfoDTO> getAllUsers() {
        List<OrderSumEntity> orders = orderSumRepository.findAll();
        return orders.stream()
                .map(order -> {


                    String merahantID = merchantId;
                    String merchantSecret = merchantSecretPay;
                    String orderID = order.getOrder_id();
                    String currency = order.getCurrency();
                    DecimalFormat df = new DecimalFormat("0.00");
                    String amountFormatted = df.format(order.getAmount());
                    String hash = getMd5(merahantID + orderID + amountFormatted + currency + getMd5(merchantSecret));

                    return new OrderInfoDTO(order.getOrder_id(), order.getAmount(), order.getCurrency(), order.getNIC(), order.getFirst_name(), order.getLast_name(), order.getEmail(), order.getPhone(), order.getAddress(), order.getCity(), order.getCountry(), merchantId, merchantSecret, formatAmount(order.getAmount()), hash);
                })
                .collect(Collectors.toList());
    }

    public OrderInfoDTO getOrderById(String id) {
        Optional<OrderSumEntity> orderOptional = orderSumRepository.findById(Long.valueOf(id));
        if (orderOptional.isPresent()) {
            OrderSumEntity order = orderOptional.get();

            String merahantID = merchantId;
            String merchantSecret = merchantSecretPay;
            String orderID = order.getOrder_id();
            String currency = order.getCurrency();
            DecimalFormat df = new DecimalFormat("0.00");
            String amountFormatted = df.format(order.getAmount());
            String hash = getMd5(merahantID + orderID + amountFormatted + currency + getMd5(merchantSecret));

            return new OrderInfoDTO(order.getOrder_id(), order.getAmount(), order.getCurrency(), order.getNIC(), order.getFirst_name(), order.getLast_name(), order.getEmail(), order.getPhone(), order.getAddress(), order.getCity(), order.getCountry(), merchantId, merchantSecret, formatAmount(order.getAmount()), hash);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public OrderSumEntity createOrder(OrderSumEntity order) {
        return orderSumRepository.save(order);
    }

    public OrderSumEntity editOrder(String orderId, OrderSumEntity updatedOrder) {
        OrderSumEntity existingOrder = orderSumRepository.findById(Long.valueOf(String.valueOf(id)))
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Update the existing order entity with the updated values
        existingOrder.setAmount(updatedOrder.getAmount());
        existingOrder.setCurrency(updatedOrder.getCurrency());
        existingOrder.setNIC(updatedOrder.getNIC());
        existingOrder.setFirst_name(updatedOrder.getFirst_name());
        existingOrder.setLast_name(updatedOrder.getLast_name());
        existingOrder.setEmail(updatedOrder.getEmail());
        existingOrder.setPhone(updatedOrder.getPhone());
        existingOrder.setAddress(updatedOrder.getAddress());
        existingOrder.setCity(updatedOrder.getCity());
        existingOrder.setCountry(updatedOrder.getCountry());
//        existingOrder.setTransaction_id(updatedOrder.getTransaction_id());
//        existingOrder.setPayment_status(updatedOrder.getPayment_status());

        // Save the updated order entity to the database
        return orderSumRepository.save(existingOrder);
    }

//    public void updatePaymentStatus(String transactionId, String status) {
//        OrderSumEntity order = orderSumRepository.findByTransaction_id(transactionId);
//        if (order != null) {
//            order.setPayment_status(status);
//            orderSumRepository.save(order);
//        }
//    }

    private OrderInfoDTO convertToDTO(OrderSumEntity order) {
        OrderInfoDTO dto = new OrderInfoDTO();
        dto.setOrder_id(order.getOrder_id());
        dto.setAmount(order.getAmount());
        dto.setCurrency(order.getCurrency());
        dto.setNIC(order.getNIC());
        dto.setFirst_name(order.getFirst_name());
        dto.setLast_name(order.getLast_name());
        dto.setEmail(order.getEmail());
        dto.setPhone(order.getPhone());
        dto.setAddress(order.getAddress());
        dto.setCity(order.getCity());
        dto.setCountry(order.getCountry());
//        dto.setTransaction_id(order.getTransaction_id());
//        dto.setPayment_status(order.getPayment_status());
        return dto;
    }


//    public OrderInfoDTO createOrder(OrderInfoDTO orderInfoDTO) {
//        OrderSumEntity order = getOrderSumEntity(orderInfoDTO);
////        String merahantID = merchantId;
////        String merchantSecret = merchantSecretPay;
////        String orderID = order.getOrder_id();
////        String currency = order.getCurrency();
////        DecimalFormat df = new DecimalFormat("0.00");
////        String amountFormatted = df.format(order.getAmount());
//        String hash = "gfgb";
//
////           merchantId, formatAmount(order.getAmount()));
//
//        return new OrderInfoDTO(order.getOrder_id(), order.getAmount(), order.getCurrency(), order.getNIC(), order.getFirst_name(), order.getLast_name(), order.getEmail(), order.getPhone(), order.getAddress(), order.getCity(), order.getCountry(), merchantId, merchantSecretPay, formatAmount(order.getAmount()), hash);
//    }

//    private static OrderSumEntity getOrderSumEntity(OrderInfoDTO orderInfoDTO) {
//        OrderSumEntity order = new OrderSumEntity();
//        order.setOrder_id(orderInfoDTO.getOrder_id());
//        order.setAmount(orderInfoDTO.getAmount());
//        order.setCurrency(orderInfoDTO.getCurrency());
//        order.setNIC(orderInfoDTO.getNIC());
//        order.setFirst_name(orderInfoDTO.getFirst_name());
//        order.setLast_name(orderInfoDTO.getLast_name());
//        order.setEmail(orderInfoDTO.getEmail());
//        order.setPhone(orderInfoDTO.getPhone());
//        order.setAddress(orderInfoDTO.getAddress());
//        order.setCity(orderInfoDTO.getCity());
//        order.setCountry(orderInfoDTO.getCountry());
//        return order;
//    }

    private String formatAmount(BigDecimal amount) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setGroupingUsed(false);
        return df.format(amount);
    }

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }
}
