package com.github.dnvriend.services;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventListenerService implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("Received event: " + event.getClass().getSimpleName() + " details: " + event.toString());
    }
}
