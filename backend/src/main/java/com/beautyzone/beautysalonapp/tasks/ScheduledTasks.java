package com.beautyzone.beautysalonapp.tasks;

import com.beautyzone.beautysalonapp.service.impl.DailyTimeSlotPopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private DailyTimeSlotPopulationService dailyTimeSlotPopulationService;

    @Scheduled(cron = "0 0 0 * * *") // Run every day at midnight
    public void prepopulateRowsDaily() {
        dailyTimeSlotPopulationService.prepopulateRowsDaily();
    }
}
