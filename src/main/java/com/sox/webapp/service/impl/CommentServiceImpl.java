package com.sox.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sox.webapp.exception.SqlFailOperationException;
import com.sox.webapp.mapper.CommentMapper;
import com.sox.webapp.model.Comment;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.CommentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> getCommentByAnimeId(String animeId,long currentPage) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("animeId",animeId)
                .orderByDesc("postDate");
        Page<Comment> page = new Page<>(currentPage,20L);
        return commentMapper.selectPage(page,wrapper).getRecords();
    }

    @Override
    public List<Comment> getCommentByUsername(String username) {
        return null;
    }

    @Override
    public ResponseDataSet<Object> insertComment(ResponseDataSet<Object> dataSet) {
        Comment comment = (Comment)dataSet.getData();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setUsername(username);
        comment.setPostDate(new Date(System.currentTimeMillis()));
        if(commentMapper.insert(comment) == 0){
            throw new SqlFailOperationException("服务器出错,请稍后尝试");
        }
        comment.setUsername(username);
        return dataSet;
    }
}
