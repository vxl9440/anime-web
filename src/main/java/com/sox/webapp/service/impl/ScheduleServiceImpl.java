package com.sox.webapp.service.impl;

import com.sox.webapp.service.ScheduleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sox.webapp.util.Constant.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {


    private final AnimeServiceImpl animeService;

    public ScheduleServiceImpl(AnimeServiceImpl animeService) {
        this.animeService = animeService;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void refillRecommendList() {
        recommendList = animeService.getRecommendAnime(15);
    }

    @Override
    @Scheduled(cron = "0 0 */1 * * ?")
    public void refillWeekdayList() {
        weekdayList.get(0).setAnimeList(animeService.getAnimeByWeekday(1,"连载"));
        weekdayList.get(1).setAnimeList(animeService.getAnimeByWeekday(2,"连载"));
        weekdayList.get(2).setAnimeList(animeService.getAnimeByWeekday(3,"连载"));
        weekdayList.get(3).setAnimeList(animeService.getAnimeByWeekday(4,"连载"));
        weekdayList.get(4).setAnimeList(animeService.getAnimeByWeekday(5,"连载"));
        weekdayList.get(5).setAnimeList(animeService.getAnimeByWeekday(6,"连载"));
        weekdayList.get(6).setAnimeList(animeService.getAnimeByWeekday(7,"连载"));
    }

    @Override
    @Scheduled(cron = "0 */10 * * * ?")
    public void refillUpdateList() {
        updateList = animeService.getUpdatedAnime(15);
    }

    @Override
    @Scheduled(cron = "0 0 0 ? * MON")
    public void refillRelatedMap() {
        List<String> animeIdList = animeService.getAllAnimeId();
        for (String animeId : animeIdList) {
            relatedMap.put(animeId,animeService.getRelatedAnimeByAnimeId(animeId,8));
        }
    }

}
