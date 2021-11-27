package com.tsystems.javaschool.projects.SBB.service.util;

import com.tsystems.javaschool.projects.SBB.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyListener {
    private final ScheduleService scheduleService;

    @EventListener
    public void handleContextStarted(ContextRefreshedEvent event) {
        scheduleService.notifyConsumer();
    }
}
