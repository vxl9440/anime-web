package com.sox.webapp.validate;

import com.sox.webapp.exception.DataFormatInvalidException;
import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.Anime;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.util.Constant;
import org.springframework.validation.BindingResult;


public class AnimeInfoValidator extends Validator{
    private final Anime anime;

    public AnimeInfoValidator(Anime anime,BindingResult result) {
        super(result);
        this.anime = anime;
    }

    @Override
    public ResponseDataSet<Object> getResult() {
        ResponseDataSet<Object> responseDataSet = ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",anime);
        if(result.hasErrors()){
            throw new DataFormatInvalidException(result.getAllErrors().get(0).toString());
        }
        return responseDataSet;
    }
}
