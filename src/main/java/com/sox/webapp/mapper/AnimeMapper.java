package com.sox.webapp.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sox.webapp.model.Anime;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnimeMapper extends BaseMapper<Anime> {

    @Select("select animeId from anime")
    List<String> getAllAnimeId();

    @Select("select a.animeId,a.chineseName,a.coverUrl " +
            "from anime a inner join anime_account_assoc aaa " +
            "on a.animeId = aaa.animeId and aaa.username = #{username}")
    List<Anime> getAnimeByUsername(@Param("username") String username);

    @Select("select * from anime " +
            "where `releasedDate` between '2011-01-01' and NOW() and `locked` = 0  and `status` = '完结'" +
            "order by RAND() limit #{size}")
    List<Anime> getRecommendAnime(@Param("size") int size);

    @Select("select * from anime " +
            "where status = '连载' " +
            "order by `updatedDate` desc limit #{size}")
    List<Anime> getUpdatedAnime(@Param("size") int size);

    @Select("SELECT DISTINCT(t.animeId),t.chineseName,t.coverUrl,t.status " +
            "FROM `anime` a INNER JOIN `anime_type_assoc` ata " +
            "ON a.animeId = ata.animeId AND a.animeId = #{animeId} AND a.locked = 0 " +
            "INNER JOIN (SELECT a2.animeId,a2.chineseName,a2.coverUrl,a2.status,ata2.typeName " +
            "            FROM `anime` a2 INNER JOIN `anime_type_assoc` ata2 " +
            "            ON a2.animeId = ata2.animeId AND a2.locked = 0) t " +
            "ON ata.typeName = t.typeName and t.animeId != #{animeId}" +
            "ORDER BY RAND() LIMIT #{size};")
    List<Anime> getRelatedAnimeByAnimeId(@Param("animeId") String animeId,@Param("size") int size);

    // 1=Sunday, 2=Monday, 3=Tuesday, 4=Wednesday, 5=Thursday, 6=Friday, 7=Saturday.
    @Select("select * from anime " +
            "where DAYOFWEEK(`releasedDate`) = #{weekday} and `status` = #{status} and `locked` = 0")
    List<Anime> getAnimeByWeekday(@Param("weekday") int weekday,@Param("status") String status);

    @Insert("insert into anime_account_assoc(username,animeId) values(#{username},#{animeId})")
    int insertLikeAnime(@Param("username") String username,@Param("animeId") String animeId);

    @Delete("delete from anime_account_assoc " +
            "where username = #{username} and animeId = #{animeId}")
    int deleteLikeAnime(@Param("username") String username,@Param("animeId") String animeId);

    @Select("select count(*) from anime_account_assoc " +
            "where username = #{username} and animeId = #{animeId}")
    int likeAnimeExist(@Param("username") String username,@Param("animeId") String animeId);


    @Select("select a.animeId,a.chineseName,s.coverUrl " +
            "from anime a inner join slide s " +
            "on a.animeId = s.animeId")
    List<Anime> getAllSlide();

    @Select("select coverUrl " +
            "from slide " +
            "where animeId = #{animeId}")
    String getSlideByAnimeId(@Param("animeId") String animeId);

    @Insert("insert into slide(animeId,coverUrl) values(#{animeId},#{coverUrl})")
    int insertAnimeSlide(@Param("animeId") String animeId,@Param("coverUrl") String coverUrl);

    @Delete("delete from slide where animeId = #{animeId}")
    int deleteSlide(@Param("animeId") String animeId);
}
