package com.sox.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sox.webapp.mapper.TypeMapper;
import com.sox.webapp.model.Type;
import com.sox.webapp.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    @Override
    public List<Type> getAllTypes() {
        return typeMapper.selectList(null);
    }

    @Override
    public List<Type> getTypeByAnimeId(String animeId) {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("animeId",animeId);
        return typeMapper.selectList(queryWrapper);
    }
}
