package com.beautyzone.beautysalonapp.tasks;

import com.beautyzone.beautysalonapp.service.impl.DailyTimeSlotPopulationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private DailyTimeSlotPopulationServiceImpl dailyTimeSlotPopulationServiceImpl;

    @Scheduled(cron = "0 0 0 * * *") // Run every day at midnight
    public void prepopulateRowsDaily() {
        dailyTimeSlotPopulationServiceImpl.prepopulateRowsDaily();
    }
}
