package com.sox.webapp.filler;

import com.sox.webapp.model.Anime;
import com.sox.webapp.util.Constant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImageDataFiller implements BaseDataFiller{
    @Override
    public void fill(List<Anime> animeList) {
        for (Anime anime : animeList) {
            if(anime.getCoverUrl() != null){
                anime.setCoverUrl(Constant.IMG_URL_ROOT+anime.getCoverUrl());
            }
        }
    }
}
