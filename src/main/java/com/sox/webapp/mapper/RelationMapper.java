package com.sox.webapp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sox.webapp.model.AnimeRelation;
import com.sox.webapp.model.Relation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationMapper extends BaseMapper<Relation> {

    @Insert("insert into anime_relation(relationId,animeId) values (#{relationId},#{animeId})")
    int insertAnimeRelation(@Param("relationId") int relationId,@Param("animeId") String animeId);

    @Delete("delete from anime_relation where relationId = #{relationId} and animeId = #{animeId}")
    int deleteAnimeRelation(@Param("relationId") int relationId,@Param("animeId") String animeId);

    @Select("select * from anime_relation where relationId = #{relationId}")
    List<AnimeRelation> getAnimeRelationByRelationId(@Param("relationId") int relationId);

    @Select("select * from anime_relation where animeId = #{animeId}")
    AnimeRelation getAnimeRelationByAnimeId(@Param("animeId") String animeId);

}
