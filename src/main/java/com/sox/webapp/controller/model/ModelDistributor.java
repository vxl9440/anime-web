package com.sox.webapp.controller.model;

import com.sox.webapp.factory.DataFillerListFactory;
import com.sox.webapp.service.impl.AnimeEpisodeServiceImpl;
import com.sox.webapp.service.impl.AnimeServiceImpl;
import com.sox.webapp.util.Constant;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;


@Component
public class ModelDistributor {

    private final AnimeServiceImpl animeService;
    private final AnimeEpisodeServiceImpl animeEpisodeService;

    public ModelDistributor(AnimeServiceImpl animeService, AnimeEpisodeServiceImpl animeEpisodeService) {
        this.animeService = animeService;
        this.animeEpisodeService = animeEpisodeService;
    }

    public void addUsernameAttr(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("username",principal.getName());
        }
    }

    public void addTypeStatusAttr(Model model){
        model.addAttribute("typeList",Constant.typeList);
        model.addAttribute("statusList",Constant.statusList);
    }

    public void addCategoryListAttr(Model model){
        model.addAttribute("yearList",Constant.yearList);
        model.addAttribute("seasonList",Constant.seasonList);
        this.addTypeStatusAttr(model);
        model.addAttribute("sortList",Constant.sortLit);
    }

    public void addCategoryResultAttr(Model model,String categories,boolean includeLocked){
        int totalNumber = animeService.getCountByCategory(categories,includeLocked);
        model.addAttribute("totalNumber",totalNumber);
        model.addAttribute("totalPage",(int)Math.ceil((double)totalNumber/Constant.PAGE_SIZE));
        model.addAttribute("animeList",animeService.getAnimeByCategories(categories,includeLocked,
                DataFillerListFactory.build(true,true,true)));
        String[] token = categories.split("-");
        model.addAttribute("yearSelected",token[0]);
        model.addAttribute("seasonSelected",token[1]);
        model.addAttribute("typeSelected",token[2]);
        model.addAttribute("statusSelected",token[3]);
        model.addAttribute("sortSelected",token[4]);
        model.addAttribute("currentPage",token[5]);

    }

    public void addSearchResultAttr(Model model,String keyword,boolean includeLocked){
        model.addAttribute("totalNumber",animeService.getCountByKeyword(keyword,includeLocked));
        model.addAttribute("animeList",animeService.getAnimeByKeyword(keyword,includeLocked,
                DataFillerListFactory.build(true,true,true)));
    }

    public void addSingleAnimeAttr(Model model,String animeId){
        model.addAttribute("videoUrlRoot",Constant.VIDEO_URL_ROOT);
        model.addAttribute("episodeList",animeEpisodeService.getEpisodeByAnimeId(animeId));
        model.addAttribute("anime",animeService.getAnimeById(animeId,
                DataFillerListFactory.build(true,true,false)));
    }

    public void addForIndex(Model model){
        model.addAttribute("recommendList", Constant.recommendList);
        model.addAttribute("weekdayList",Constant.weekdayList);
        model.addAttribute("updateList",Constant.updateList);
    }

}
