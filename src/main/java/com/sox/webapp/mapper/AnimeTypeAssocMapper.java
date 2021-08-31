package com.sox.webapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sox.webapp.model.AnimeTypeAssoc;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeTypeAssocMapper extends BaseMapper<AnimeTypeAssoc> {

    @Select("select typeName from anime_type_assoc where animeId = #{animeId}")
    List<String> getTypesByAnimeId(@Param("animeId") String animeId);
}
