package com.sox.webapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sox.webapp.model.AnimeEpisode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnimeEpisodeMapper extends BaseMapper<AnimeEpisode> {

    @Select("select episodeNumber from anime_episode where animeId = #{animeId}")
    List<String> getAllEpisodeByAnimeId(@Param("animeId") String animeId);
}
