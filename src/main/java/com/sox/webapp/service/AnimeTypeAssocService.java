package com.sox.webapp.service;

import com.sox.webapp.model.AnimeTypeAssoc;

import java.util.List;

public interface AnimeTypeAssocService {
    int insertAnimeTypeAssoc(AnimeTypeAssoc animeTypeAssoc);
    List<String> getTypesByAnimeId(String animeId);
    int deleteTypesByAnimeId(String animeId);
}
