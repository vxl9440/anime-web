package com.sox.webapp.service;

import com.sox.webapp.model.Type;

import java.util.List;

public interface TypeService {
    List<Type> getAllTypes();
    List<Type> getTypeByAnimeId(String animeId);
}
