package com.group.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class OrderSumEntity {
    @Id
    private String order_id;
    //    @Column(name = "amount")
    private BigDecimal amount;
    //    @Column(name = "currency")
    private String currency;
    //    @Column(name = "NIC")
    private String NIC;
    //    @Column(name = "first_name")
    private String first_name;
    //    @Column(name = "last_name")
    private String last_name;
    //    @Column(name = "email")
    private String email;
    //    @Column(name = "phone")
    private String phone;
    //    @Column(name = "address")
    private String address;
    //    @Column(name = "city")
    private String city;
    //    @Column(name = "country")
    private String country;

//    private String transaction_id;
//    private String payment_status;


    //    public String getTransaction_id() {
//        return transaction_id;
//    }
//
//    public void setTransaction_id(String transaction_id) {
//        this.transaction_id = transaction_id;
//    }
//
//    public String getPayment_status() {
//        return payment_status;
//    }
//
//    public void setPayment_status(String payment_status) {
//        this.payment_status = payment_status;
//    }
}
