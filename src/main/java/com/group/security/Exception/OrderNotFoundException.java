package com.group.security.Exception;

public class OrderNotFoundException extends RuntimeException  {
    public OrderNotFoundException(Long id){
        super("Order not found id"+id);
    }
}
