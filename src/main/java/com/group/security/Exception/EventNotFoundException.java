package com.group.security.Exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(Long id){
        super("Event not found id"+id);
    }
}