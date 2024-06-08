package com.group.security.Exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(Integer id){
        super("Event not found id"+id);
    }
}