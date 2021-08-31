/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: AdminRedirectController
 * @packageName: com.sox.webapp.controller.admin
 * @description: A class to handle http requests and responses for admin redirect
 * @date: 2021-07-21
 */
package com.sox.webapp.controller.admin;

import com.sox.webapp.controller.model.ModelDistributor;

import com.sox.webapp.service.impl.AnimeServiceImpl;
import com.sox.webapp.service.impl.RelationServiceImpl;
import com.sox.webapp.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AdminRedirectController {

    /** model distributor **/
    private final ModelDistributor modelDistributor;
    /** relation service **/
    private final RelationServiceImpl relationService;
    /** anime service **/
    private final AnimeServiceImpl animeService;

    /**
     * @param modelDistributor injected from bean container
     * @param relationService injected from bean container
     * @param animeService injected from bean container
     */
    public AdminRedirectController(ModelDistributor modelDistributor,
                                   RelationServiceImpl relationService,
                                   AnimeServiceImpl animeService) {
        this.modelDistributor = modelDistributor;
        this.relationService = relationService;
        this.animeService = animeService;
    }

    /**
     *
     * @return redirect to /admin/adminAnimeCategory/all
     */
    @RequestMapping("/admin")
    public String redirectAdmin(){
        return "redirect:/admin/adminAnimeCategory/all";
    }

    /**
     * @param model model for thymeleaf
     * @return direct to adminLogin page
     */
    @RequestMapping("/admin/loginFail")
    public String redirectLogin(Model model){
        model.addAttribute("showError",true);
        return "/admin/adminLogin";
    }

    /**
     * @param principal contains current user information
     * @return direct to adminLogin page if user haven't login, redirect to /admin/adminAnimeCategory/all
     *         if user have login
     */
    @RequestMapping("/admin/adminLogin")
    public String redirectLogin2(Principal principal){
        return principal == null?"/admin/adminLogin":"redirect:/admin/adminAnimeCategory/all";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @return direct to adminSpace page
     */
    @RequestMapping("/admin/space")
    public String redirectSpace(Model model,Principal principal){
        modelDistributor.addUsernameAttr(model,principal);
        return "/admin/adminSpace";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @param categories contains all options to classify
     * @return direct to adminAnimeCategory page
     */
    @RequestMapping(value = "/admin/adminAnimeCategory/{categories}",produces="text/plain;charset=UTF-8")
    public String redirectLoginSuccess(Model model,Principal principal,@PathVariable("categories") String categories){
        if("all".equals(categories)) categories = "全部-全部-全部-全部-时间-1";
        if(categories.split("-").length != 6) return "/error/404";
        modelDistributor.addUsernameAttr(model,principal);
        modelDistributor.addCategoryResultAttr(model,categories,true);
        modelDistributor.addCategoryListAttr(model);
        return "/admin/adminAnimeCategory";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @param keyword anime searching keyword from user
     * @return direct to adminSearchResult page
     */
    @RequestMapping("/admin/searchResult")
    public String redirectSearchResult(Model model,Principal principal,
                                       @RequestParam("keyword") String keyword){
        modelDistributor.addUsernameAttr(model,principal);
        if("locked -all".equals(keyword)){
            model.addAttribute("totalNumber",animeService.getCountByLocked());
            model.addAttribute("animeList",animeService.getAllLockedAnime());
        }else{
            modelDistributor.addSearchResultAttr(model,keyword,true);
        }
        return "/admin/adminSearchResult";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @return direct to adminCreateAnime page
     */
    @RequestMapping("/admin/adminCreateAnime")
    public String redirectCreateAnime(Model model, Principal principal){
        modelDistributor.addUsernameAttr(model,principal);
        modelDistributor.addTypeStatusAttr(model);
        return "/admin/adminCreateAnime";
    }


    /**
     *
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @param animeId targeted anime id
     * @return direct to adminRewriteAnime page
     */
    @RequestMapping("/admin/adminRewriteAnime/{animeId}")
    public String redirectRewriteAnime(Model model, Principal principal,@PathVariable("animeId") String animeId){
        modelDistributor.addUsernameAttr(model,principal);
        modelDistributor.addTypeStatusAttr(model);
        modelDistributor.addSingleAnimeAttr(model,animeId);
        model.addAttribute("relation",relationService.getRelationByAnimeId(animeId));
        return "/admin/adminRewriteAnime";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @return direct to adminAnimeRelation page
     */
    @RequestMapping("/admin/adminAnimeRelation")
    public String redirectRewriteAnime(Model model, Principal principal){
        modelDistributor.addUsernameAttr(model,principal);
        model.addAttribute("relationList",relationService.getAllRelation());
        return "/admin/adminAnimeRelation";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @param keyword relation searching keyword
     * @return direct to adminAnimeRelation page
     */
    @RequestMapping("/admin/adminRelationSearch")
    public String redirectRelationSearch(Model model,Principal principal,@RequestParam("keyword") String keyword){
        modelDistributor.addUsernameAttr(model,principal);
        model.addAttribute("relationList",relationService.getRelationByKeyword(keyword));
        return "/admin/adminAnimeRelation";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @return direct to adminSlide page
     */
    @RequestMapping("/admin/adminSlide")
    public String redirectSlide(Model model,Principal principal){
        modelDistributor.addUsernameAttr(model,principal);
        model.addAttribute("slideList",animeService.getAllSlide());
        return "/admin/adminSlide";
    }

    /**
     * @param model model for thymeleaf
     * @param principal contains current user information
     * @return direct to adminCustom page
     */
    @RequestMapping("/admin/adminCustom")
    public String redirectCustom(Model model,Principal principal){
        modelDistributor.addUsernameAttr(model,principal);
        model.addAttribute("notification", Constant.notificationMessage);
        return "/admin/adminCustom";
    }
}
