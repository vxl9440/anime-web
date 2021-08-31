package com.sox.webapp.service;


import com.sox.webapp.filler.BaseDataFiller;
import com.sox.webapp.model.Anime;
import com.sox.webapp.model.ResponseDataSet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface AnimeService {
    Anime getAnimeById(String animeId,List<BaseDataFiller> fillerList);
    List<String> getAllAnimeId();
    List<Anime> getAnimeRandomly(int pageSize,List<BaseDataFiller> fillerList);
    List<Anime> getAnimeByKeyword(String keyword,boolean includeLocked,List<BaseDataFiller> fillerList);
    List<Anime> getAnimeByCategories(String categories, boolean includeLocked,List<BaseDataFiller> fillerList);
    List<Anime> getAnimeByUsername(String username,int currentPage,List<BaseDataFiller> fillerList);
    List<Anime> getRecommendAnime(int size);
    List<Anime> getUpdatedAnime(int size);
    List<Anime> getRelatedAnimeByAnimeId(String animeId,int size);
    List<Anime> getAllLockedAnime();
    List<Anime> getAllSlide();
    List<Anime> getAnimeByWeekday(int weekday,String status);
    int getCountByCategory(String categories,boolean includeLocked);
    int getCountByKeyword(String keyword,boolean includeLocked);
    int getCountByLocked();
    int deleteAnime(String animeId);
    int likeAnimeExist(String username,String animeId);
    ResponseDataSet<Object> insertSlide(String animeId, MultipartFile imageFile);
    ResponseDataSet<Object> deleteSlide(String animeId);
    ResponseDataSet<Object> insertAnime(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> insertLikeAnime(String username,String animeId);
    ResponseDataSet<Object> deleteLikeAnime(String username,String animeId);
    ResponseDataSet<Object> updateAnimeCover(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> updateAnimeInfo(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> updateAnimeLockStatus(String animeId,Integer lockStatus);
}
