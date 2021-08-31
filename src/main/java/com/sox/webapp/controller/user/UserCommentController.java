package com.sox.webapp.controller.user;

import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.Comment;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.impl.CommentServiceImpl;
import com.sox.webapp.util.Constant;
import com.sox.webapp.validate.CommentValidator;
import com.sox.webapp.validate.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserCommentController {


    private final CommentServiceImpl commentService;

    public UserCommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/insertComment")
    public ResponseDataSet<Object> insertComment(@RequestBody @Valid Comment comment, BindingResult result){
        Validator validator = new CommentValidator(result,comment);
        return commentService.insertComment(validator.getResult());
    }

    @RequestMapping("/moreComment")
    public ResponseDataSet<Object> requestComment(@RequestParam("animeId") String animeId,
                                                  @RequestParam("nextPageNumber") Integer nextPageNumber){
        return ReturnResultFactory.build(Constant.SUCCESS_CODE,
                "SUCCESS",commentService.getCommentByAnimeId(animeId,nextPageNumber));
    }
}
