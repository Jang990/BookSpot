package com.bookspot.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Events {
    private static ApplicationEventPublisher publisher;

    @Autowired
    private Events(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if(publisher != null)
            publisher.publishEvent(event);
    }
}