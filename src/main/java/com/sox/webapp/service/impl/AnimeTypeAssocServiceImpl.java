package com.sox.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sox.webapp.mapper.AnimeTypeAssocMapper;
import com.sox.webapp.model.AnimeTypeAssoc;
import com.sox.webapp.service.AnimeTypeAssocService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeTypeAssocServiceImpl implements AnimeTypeAssocService {

    private final AnimeTypeAssocMapper animeTypeAssocMapper;

    public AnimeTypeAssocServiceImpl(AnimeTypeAssocMapper animeTypeAssocMapper) {
        this.animeTypeAssocMapper = animeTypeAssocMapper;
    }

    @Override
    public int insertAnimeTypeAssoc(AnimeTypeAssoc animeTypeAssoc) {
        return animeTypeAssocMapper.insert(animeTypeAssoc);
    }

    @Override
    public List<String> getTypesByAnimeId(String animeId) {
        return animeTypeAssocMapper.getTypesByAnimeId(animeId);
    }

    @Override
    public int deleteTypesByAnimeId(String animeId) {
        QueryWrapper<AnimeTypeAssoc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("animeId",animeId);
        return animeTypeAssocMapper.delete(queryWrapper);
    }
}
