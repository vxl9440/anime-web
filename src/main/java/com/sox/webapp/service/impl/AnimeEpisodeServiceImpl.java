package com.sox.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sox.webapp.exception.FileFailOperationException;
import com.sox.webapp.exception.SqlFailOperationException;
import com.sox.webapp.mapper.AnimeEpisodeMapper;
import com.sox.webapp.model.AnimeEpisode;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.AnimeEpisodeService;
import com.sox.webapp.util.Constant;
import com.sox.webapp.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AnimeEpisodeServiceImpl implements AnimeEpisodeService {

    private final AnimeEpisodeMapper animeEpisodeMapper;

    public AnimeEpisodeServiceImpl(AnimeEpisodeMapper animeEpisodeMapper) {
        this.animeEpisodeMapper = animeEpisodeMapper;
    }

    @Override
    public List<AnimeEpisode> getEpisodeByAnimeId(String animeId) {
        QueryWrapper<AnimeEpisode> wrapper = new QueryWrapper<>();
        wrapper.eq("animeId",animeId).orderByAsc("episodeNumber");
        List<AnimeEpisode> animeEpisodeList = animeEpisodeMapper.selectList(wrapper);
        Collections.sort(animeEpisodeList);
        return animeEpisodeList;
    }

    @Override
    public List<String> getAllEpisodeByAnimeId(String animeId) {
        return animeEpisodeMapper.getAllEpisodeByAnimeId(animeId);
    }

    @Override
    public AnimeEpisode getEpisodeByIdAndNumber(String animeId, String episodeNumber) {
        QueryWrapper<AnimeEpisode> wrapper = new QueryWrapper<>();
        wrapper.eq("animeId",animeId).eq("episodeNumber",episodeNumber);
        return animeEpisodeMapper.selectOne(wrapper);
    }

    @Override
    public AnimeEpisode getEpisodeByIdAndMd5(String animeId, String md5) {
        QueryWrapper<AnimeEpisode> wrapper = new QueryWrapper<>();
        wrapper.eq("animeId",animeId).eq("md5",md5);
        return animeEpisodeMapper.selectOne(wrapper);
    }



    @Override
    @Transactional
    public ResponseDataSet<Object> insertAnimeEpisode(ResponseDataSet<Object> dataSet) {
        AnimeEpisode animeEpisode = (AnimeEpisode) dataSet.getData();
        if(this.getEpisodeByIdAndNumber(animeEpisode.getAnimeId(),animeEpisode.getEpisodeNumber()) != null){
            throw new SqlFailOperationException("该集数已存在");
        }

        String fileStoreDir = Constant.VIDEO_RESOURCE_ROOT+animeEpisode.getAnimeId();
        String[] fileToken = Objects.requireNonNull(animeEpisode.getFile().getOriginalFilename()).split("\\.");
        String fileExtension = fileToken[fileToken.length - 1];


        String md5 = FileUtil.getMD5(animeEpisode.getFile());

        if(this.getEpisodeByIdAndMd5(animeEpisode.getAnimeId(),md5) != null){
            throw new SqlFailOperationException("该视频文件已存在");
        }

        if(!FileUtil.write(animeEpisode.getFile(),fileStoreDir,md5+"."+fileExtension)){
            throw new FileFailOperationException("Oops 服务器错误，请重试");
        }

        animeEpisode.setMd5(md5);
        animeEpisode.setVideoType(fileExtension);
        if(animeEpisodeMapper.insert(animeEpisode) == 0){
            throw new SqlFailOperationException("Oops 服务器错误，请重试");
        }

        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> updateAnimeEpisode(ResponseDataSet<Object> dataSet) {
        AnimeEpisode animeEpisode = (AnimeEpisode) dataSet.getData();
        UpdateWrapper<AnimeEpisode> wrapper = new UpdateWrapper<>();
        wrapper.eq("animeId",animeEpisode.getAnimeId())
                .eq("md5",animeEpisode.getMd5())
                .set("episodeTitle",animeEpisode.getEpisodeTitle())
                .set("episodeNumber",animeEpisode.getEpisodeNumber());
        if(animeEpisodeMapper.update(null,wrapper) == 0){
            throw new SqlFailOperationException("Oops 服务器错误，请重试");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> deleteAnimeEpisode(ResponseDataSet<Object> dataSet) {
        AnimeEpisode animeEpisode = (AnimeEpisode) dataSet.getData();
        QueryWrapper<AnimeEpisode> wrapper = new QueryWrapper<>();
        wrapper.eq("animeId",animeEpisode.getAnimeId()).eq("md5",animeEpisode.getMd5());
        if(animeEpisodeMapper.delete(wrapper) == 0){
            throw new SqlFailOperationException("Oops 服务器错误，请重试");
        }

        //delete file
        File file = new File(Constant.VIDEO_RESOURCE_ROOT+animeEpisode.getAnimeId()+
                                   "\\"+animeEpisode.getMd5()+"."+animeEpisode.getVideoType());
        if(!file.delete()){
            System.out.println("IGNORE 文件不存在");
        }
        dataSet.setData(null);
        return dataSet;
    }
}
