package com.sox.webapp.filler;

import com.sox.webapp.model.Anime;
import com.sox.webapp.service.impl.AnimeTypeAssocServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TypeDataFiller implements BaseDataFiller{

    private final AnimeTypeAssocServiceImpl animeTypeAssocService;

    public TypeDataFiller(AnimeTypeAssocServiceImpl animeTypeAssocService) {
        this.animeTypeAssocService = animeTypeAssocService;
    }

    @Override
    public void fill(List<Anime> animeList) {
        for (Anime anime : animeList) {
            anime.setTypes(animeTypeAssocService.getTypesByAnimeId(anime.getAnimeId()));
        }
    }
}
