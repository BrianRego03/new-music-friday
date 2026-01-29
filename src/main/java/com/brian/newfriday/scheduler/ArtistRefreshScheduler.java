package com.brian.newfriday.scheduler;

import com.brian.newfriday.service.ArtistRefreshService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ArtistRefreshScheduler {
    private final ArtistRefreshService artistRefreshService;

    public ArtistRefreshScheduler(ArtistRefreshService artistRefreshService) {
        this.artistRefreshService = artistRefreshService;
    }

    @Scheduled(cron = "0 0 0 * * 0-4")
    @Scheduled(cron = "0 0 * * * 5")
    @Scheduled(cron = "0 0 */4 * * 6")
    public void scheduleRefresh(){
        artistRefreshService.refreshArtistData();
    }
}
