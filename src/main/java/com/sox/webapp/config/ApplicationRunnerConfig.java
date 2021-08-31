/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: ApplicationRunnerConfig
 * @packageName: com.sox.webapp.config
 * @description: A class to load data when springboot starts
 * @date: 2021-07-20
 */
package com.sox.webapp.config;

import com.sox.webapp.model.Type;
import com.sox.webapp.model.Weekday;
import com.sox.webapp.service.impl.AnimeServiceImpl;
import com.sox.webapp.service.impl.TypeServiceImpl;
import com.sox.webapp.util.Constant;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sox.webapp.util.Constant.weekdayList;
import static com.sox.webapp.util.Constant.recommendList;
import static com.sox.webapp.util.Constant.updateList;
import static com.sox.webapp.util.Constant.yearList;
import static com.sox.webapp.util.Constant.typeList;
import static com.sox.webapp.util.Constant.relatedMap;

@Component
public class ApplicationRunnerConfig implements ApplicationRunner {

    /** type service **/
    private final TypeServiceImpl typeService;
    /** anime service **/
    private final AnimeServiceImpl animeService;

    /**
     * @param typeService injected from bean container
     * @param animeService injected from bean container
     */
    public ApplicationRunnerConfig(TypeServiceImpl typeService,
                                   AnimeServiceImpl animeService) {
        this.typeService = typeService;
        this.animeService = animeService;
    }

    /**
     * This function would run when springboot starts
     * @param args commandline arguments
     */
    @Override
    public void run(ApplicationArguments args){
        fillYearList();
        fillTypeList();
        fillWeekdayList();
        fillRecommendList();
        fillUpdateList();
        fillRelatedMap();
        System.out.println("数据填充完毕");
    }

    /**
     * fill the year list
     */
    private void fillYearList(){
        Constant.yearList.add("全部");
        for(int i = 2021;i >= 2005;i--){ // fill year list
            yearList.add(Integer.toString(i));
        }
    }

    /**
     * fill the type list
     */
    private void fillTypeList(){
        typeList = typeService.getAllTypes(); // fill type list
        typeList.add(0,new Type("全部",0));
    }

    /**
     * fill the weekday list
     */
    private void fillWeekdayList(){
        weekdayList.add(new Weekday("Sun","日",animeService.getAnimeByWeekday(1,"连载")));
        weekdayList.add(new Weekday("Mon","一",animeService.getAnimeByWeekday(2,"连载")));
        weekdayList.add(new Weekday("Tue","二",animeService.getAnimeByWeekday(3,"连载")));
        weekdayList.add(new Weekday("Wed","三",animeService.getAnimeByWeekday(4,"连载")));
        weekdayList.add(new Weekday("Thu","四",animeService.getAnimeByWeekday(5,"连载")));
        weekdayList.add(new Weekday("Fri","五",animeService.getAnimeByWeekday(6,"连载")));
        weekdayList.add(new Weekday("Sat","六",animeService.getAnimeByWeekday(7,"连载")));
    }

    /**
     * fill the recommend list
     */
    private void fillRecommendList(){
        recommendList = animeService.getRecommendAnime(15);
    }

    /**
     * fill the update list
     */
    private void fillUpdateList(){
        updateList = animeService.getUpdatedAnime(15);
    }

    /**
     * fill the related map
     */
    private void fillRelatedMap(){
        List<String> animeIdList = animeService.getAllAnimeId();
        for (String animeId : animeIdList) {
            relatedMap.put(animeId,animeService.getRelatedAnimeByAnimeId(animeId,8));
        }
    }
}
