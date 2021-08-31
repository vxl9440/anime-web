package com.sox.webapp.service;

import com.sox.webapp.model.Comment;
import com.sox.webapp.model.ResponseDataSet;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentByAnimeId(String animeId,long currentPage);
    List<Comment> getCommentByUsername(String username);
    ResponseDataSet<Object> insertComment(ResponseDataSet<Object> dataSet);

}
