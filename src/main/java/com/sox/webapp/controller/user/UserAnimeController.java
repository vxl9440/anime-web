package com.sox.webapp.controller.user;


import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.impl.AnimeServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserAnimeController {

    private final AnimeServiceImpl animeService;

    public UserAnimeController(AnimeServiceImpl animeService) {
        this.animeService = animeService;
    }

    @RequestMapping("/insertLike")
    public ResponseDataSet<Object> insertLike(@RequestParam("animeId") String animeId, Principal principal){
        return animeService.insertLikeAnime(principal.getName(),animeId);
    }

    @RequestMapping("/deleteLike")
    public ResponseDataSet<Object> deleteLike(@RequestParam("animeId") String animeId, Principal principal){
        return animeService.deleteLikeAnime(principal.getName(),animeId);
    }
}
