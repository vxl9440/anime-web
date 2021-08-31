package com.sox.webapp.controller.user;

import com.sox.webapp.controller.model.ModelDistributor;
import com.sox.webapp.factory.DataFillerListFactory;
import com.sox.webapp.model.Anime;
import com.sox.webapp.service.impl.AnimeEpisodeServiceImpl;
import com.sox.webapp.service.impl.AnimeServiceImpl;
import com.sox.webapp.service.impl.CommentServiceImpl;
import com.sox.webapp.service.impl.RelationServiceImpl;
import com.sox.webapp.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserRedirectController {
    private final ModelDistributor modelDistributor;
    private final AnimeEpisodeServiceImpl animeEpisodeService;
    private final AnimeServiceImpl animeService;
    private final CommentServiceImpl commentService;
    private final RelationServiceImpl relationService;

    public UserRedirectController(ModelDistributor modelDistributor,
                                  AnimeEpisodeServiceImpl animeEpisodeService,
                                  AnimeServiceImpl animeService,
                                  CommentServiceImpl commentService,
                                  RelationServiceImpl relationService) {
        this.modelDistributor = modelDistributor;
        this.animeEpisodeService = animeEpisodeService;
        this.animeService = animeService;
        this.commentService = commentService;
        this.relationService = relationService;
    }

    @RequestMapping("/")
    public String redirectHome(Model model, Principal principal){
        modelDistributor.addUsernameAttr(model,principal);
        modelDistributor.addForIndex(model);
        model.addAttribute("slideList",animeService.getAllSlide());
        model.addAttribute("notification",Constant.notificationMessage);
        return "/user/index";
    }

    @RequestMapping("/index")
    public String redirectHome2(){
        return "redirect:/";
    }

    @RequestMapping("/category/{categories}")
    public String redirectCategory(Model model,Principal principal,@PathVariable String categories){
        if("all".equals(categories)) categories = "全部-全部-全部-全部-时间-1";
        if(categories.split("-").length != 6) return "/error/404";
        modelDistributor.addUsernameAttr(model,principal);
        modelDistributor.addCategoryResultAttr(model,categories,false);
        modelDistributor.addCategoryListAttr(model);
        model.addAttribute("notification",Constant.notificationMessage);
        return "/user/category";
    }

    @RequestMapping("/search")
    public String redirectSearch(Model model,Principal principal,@RequestParam("keyword") String keyword){
        modelDistributor.addUsernameAttr(model,principal);
        modelDistributor.addSearchResultAttr(model,keyword,false);
        model.addAttribute("notification",Constant.notificationMessage);
        return "/user/search";
    }

    @RequestMapping("/detail/{animeId}")
    public String redirectDetail(Model model,Principal principal,@PathVariable String animeId){
        modelDistributor.addUsernameAttr(model,principal);
        model.addAttribute("episodeList",animeEpisodeService.getEpisodeByAnimeId(animeId));
        model.addAttribute("anime",animeService.getAnimeById(animeId,
                DataFillerListFactory.build(true,true,false)));
        model.addAttribute("commentList",commentService.getCommentByAnimeId(animeId,1L));
        model.addAttribute("relation",relationService.getRelationByAnimeId(animeId));
        model.addAttribute("relatedList",Constant.relatedMap.get(animeId));
        model.addAttribute("notification",Constant.notificationMessage);
        if(principal != null){
            model.addAttribute("likeExist",animeService.likeAnimeExist(principal.getName(),animeId));
        }
        return "/user/detail";
    }

    @RequestMapping("/login")
    public String redirectLogin(Principal principal,Model model){
        model.addAttribute("notification",Constant.notificationMessage);
        return principal == null?"/user/login":"redirect:/";
    }

    @RequestMapping("/loginFail")
    public String redirectFailLogin(Model model){
        model.addAttribute("notification",Constant.notificationMessage);
        model.addAttribute("showError",true);
        return "/user/login";
    }

    @RequestMapping("/profile")
    public String redirectProfile(Model model,Principal principal){
        modelDistributor.addUsernameAttr(model,principal);
        model.addAttribute("notification",Constant.notificationMessage);
        model.addAttribute("likeAnimeList",animeService.getAnimeByUsername(principal.getName(),-1,
                DataFillerListFactory.build(false,true,false)));
        return "/user/profile";
    }

    @RequestMapping("/play/{animeId}/{md5}")
    public String redirectPlay(Model model, Principal principal,
                               @PathVariable("animeId") String animeId,
                               @PathVariable("md5") String md5){
        modelDistributor.addUsernameAttr(model,principal);
        Anime anime = animeService.getAnimeById(animeId,
                DataFillerListFactory.build(true,true,false));
        if(anime == null){
            return "/error/404";
        }
        model.addAttribute("anime",anime);
        model.addAttribute("videoUrlRoot", Constant.VIDEO_URL_ROOT);
        model.addAttribute("currentEpisode",animeEpisodeService.getEpisodeByIdAndMd5(animeId,md5));
        model.addAttribute("episodeList",animeEpisodeService.getEpisodeByAnimeId(animeId));
        model.addAttribute("commentList",commentService.getCommentByAnimeId(animeId,1L));
        model.addAttribute("relation",relationService.getRelationByAnimeId(animeId));
        model.addAttribute("relatedList",Constant.relatedMap.get(animeId));
        model.addAttribute("notification",Constant.notificationMessage);
        return "/user/play";
    }
}
