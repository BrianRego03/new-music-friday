package com.brian.newfriday.scheduler;

import com.brian.newfriday.service.ArtistRefreshService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

@Component
public class ArtistRefreshScheduler {
    private final ArtistRefreshService artistRefreshService;

    public ArtistRefreshScheduler(ArtistRefreshService artistRefreshService) {
        this.artistRefreshService = artistRefreshService;
    }

    //@Scheduled(cron = "0 * * * * *", zone = "Asia/Kolkata")
    @Schedules({
        @Scheduled(cron = "0 0 0 * * 0-4", zone = "Asia/Kolkata"),
        @Scheduled(cron = "0 0 * * * 5", zone = "Asia/Kolkata"),
        @Scheduled(cron = "0 0 */4 * * 6", zone = "Asia/Kolkata")
    })
    public void scheduleRefresh(){
        artistRefreshService.refreshArtistData();
    }
}
