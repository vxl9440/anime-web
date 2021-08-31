package com.sox.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sox.webapp.exception.DataFormatInvalidException;
import com.sox.webapp.exception.SqlFailOperationException;
import com.sox.webapp.mapper.RelationMapper;
import com.sox.webapp.model.Anime;
import com.sox.webapp.model.AnimeRelation;
import com.sox.webapp.model.Relation;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.RelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {

    private final RelationMapper relationMapper;
    private final AnimeServiceImpl animeService;

    public RelationServiceImpl(RelationMapper relationMapper, AnimeServiceImpl animeService) {
        this.relationMapper = relationMapper;
        this.animeService = animeService;
    }


    @Override
    @Transactional
    public ResponseDataSet<Object> insertRelation(ResponseDataSet<Object> dataSet) {
        Relation relation = (Relation) dataSet.getData();
        if(relationMapper.insert(relation) == 0){
            throw new SqlFailOperationException("出错了，稍后尝试");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> insertAnimeRelation(ResponseDataSet<Object> dataSet) {
        AnimeRelation animeRelation = (AnimeRelation)dataSet.getData();
        if(relationMapper.getAnimeRelationByAnimeId(animeRelation.getAnimeId()) != null){
            throw new DataFormatInvalidException("该动画已存在某个关联里");
        }
        if(relationMapper.insertAnimeRelation(animeRelation.getRelationId(),animeRelation.getAnimeId()) == 0){
            throw new SqlFailOperationException("出错了，稍后尝试");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> updateRelation(ResponseDataSet<Object> dataSet) {
        Relation relation = (Relation) dataSet.getData();
        UpdateWrapper<Relation> wrapper = new UpdateWrapper<>();
        wrapper.eq("relationId",relation.getRelationId()).set("relationName",relation.getRelationName());
        if(relationMapper.update(null,wrapper) == 0){
            throw new SqlFailOperationException("出错了，稍后尝试");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> deleteAnimeRelation(ResponseDataSet<Object> dataSet) {
        AnimeRelation animeRelation = (AnimeRelation)dataSet.getData();
        if(relationMapper.deleteAnimeRelation(animeRelation.getRelationId(),animeRelation.getAnimeId()) == 0){
            throw new SqlFailOperationException("出错了，稍后尝试");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> deleteRelation(ResponseDataSet<Object> dataSet) {
        Relation relation = (Relation) dataSet.getData();
        QueryWrapper<Relation> wrapper = new QueryWrapper<>();
        wrapper.eq("relationId",relation.getRelationId());
        if(relationMapper.delete(wrapper) == 0){
            throw new SqlFailOperationException("出错了，稍后尝试");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    public List<Relation> getAllRelation() {
        List<Relation> relationList = relationMapper.selectList(null);
        fillRelationAnimeList(relationList);
        return relationList;
    }

    @Override
    public List<Relation> getRelationByKeyword(String keyword) {
        QueryWrapper<Relation> wrapper = new QueryWrapper<>();
        String [] keys = keyword.split("[\\s-,]");
        if(keys.length != 0){
            QueryWrapper<Relation> temp = wrapper;
            for (String key : keys) {
                temp = temp.like("relationName", key).or();
            }
            List<Relation> relationList = relationMapper.selectList(wrapper);
            fillRelationAnimeList(relationList);
            return relationList;
        }else{
            return null;
        }
    }

    @Override
    public Relation getRelationByRelationId(Integer relationId) {
        QueryWrapper<Relation> wrapper = new QueryWrapper<>();
        wrapper.eq("relationId",relationId);
        List<Relation> relationList = relationMapper.selectList(wrapper);
        fillRelationAnimeList(relationList);
        return relationList.get(0);
    }

    @Override
    public Relation getRelationByAnimeId(String animeId) {
        AnimeRelation animeRelation = relationMapper.getAnimeRelationByAnimeId(animeId);
        if(animeRelation != null){
            return this.getRelationByRelationId(animeRelation.getRelationId());
        }else{
            return null;
        }
    }

    private void fillRelationAnimeList(List<Relation> relationList){
        for (Relation relation : relationList) {
            List<AnimeRelation> animeRelationList = relationMapper.getAnimeRelationByRelationId(relation.getRelationId());
            List<Anime> animeList = new ArrayList<>();
            for (AnimeRelation animeRelation : animeRelationList) {
                animeList.add(animeService.getAnimeById(animeRelation.getAnimeId(),null));
            }
            animeList.sort(Comparator.comparing(Anime::getChineseName));
            relation.setAnimeList(animeList);
        }
    }
}
