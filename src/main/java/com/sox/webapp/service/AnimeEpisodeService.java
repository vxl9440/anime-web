package com.sox.webapp.service;

import com.sox.webapp.model.AnimeEpisode;
import com.sox.webapp.model.ResponseDataSet;

import java.util.List;

public interface AnimeEpisodeService {
    List<AnimeEpisode> getEpisodeByAnimeId(String animeId);
    List<String> getAllEpisodeByAnimeId(String animeId);
    AnimeEpisode getEpisodeByIdAndNumber(String animeId,String episodeNumber);
    AnimeEpisode getEpisodeByIdAndMd5(String animeId,String md5);
    ResponseDataSet<Object> insertAnimeEpisode(ResponseDataSet<Object> animeEpisode);
    ResponseDataSet<Object> updateAnimeEpisode(ResponseDataSet<Object> animeEpisode);
    ResponseDataSet<Object> deleteAnimeEpisode(ResponseDataSet<Object> animeEpisode);
}
