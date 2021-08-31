package com.sox.webapp.service;

import com.sox.webapp.model.Relation;
import com.sox.webapp.model.ResponseDataSet;

import java.util.List;

public interface RelationService {
    ResponseDataSet<Object> insertRelation(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> insertAnimeRelation(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> updateRelation(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> deleteAnimeRelation(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> deleteRelation(ResponseDataSet<Object> dataSet);
    List<Relation> getAllRelation();
    List<Relation> getRelationByKeyword(String keyword);
    Relation getRelationByRelationId(Integer relationId);
    Relation getRelationByAnimeId(String animeId);
}
