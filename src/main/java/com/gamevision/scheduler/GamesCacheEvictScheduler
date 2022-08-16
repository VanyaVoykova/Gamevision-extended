package com.gamevision.scheduler;

import com.gamevision.service.GameService;
import org.springframework.scheduling.annotation.Scheduled;

public class GamesCacheEvictScheduler {
    private GameService gameService;

    @Scheduled(cron = "0 0 2 * * 2") //0 secs 0 mins 2 AM each Tuesday
    public void clearGamesCache() {
        gameService.refreshCache();
    }

    @Scheduled(cron = "0 0 3 * * 2") //0 secs 0 mins 3 AM each Tuesday
    public void clearCarouselCache() {
        gameService.refreshCarouselCache();
    }
}
