package com.sox.webapp.validate;

import com.sox.webapp.exception.DataFormatInvalidException;
import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.Comment;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.util.Constant;
import org.springframework.validation.BindingResult;

public class CommentValidator extends Validator{

    private final Comment comment;

    public CommentValidator(BindingResult result,Comment comment) {
        super(result);
        this.comment = comment;
    }

    @Override
    public ResponseDataSet<Object> getResult() {
        if(result.hasErrors()){
            throw new DataFormatInvalidException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",comment);
    }
}
