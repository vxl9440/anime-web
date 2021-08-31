package com.sox.webapp.exception;

import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.ResponseDataSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.nio.file.AccessDeniedException;

import static com.sox.webapp.util.Constant.FAIL_CODE;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    @ResponseBody
    public ResponseDataSet<Object> exceptionHandler(HttpServletRequest req, UserExistException userExistException){
        log.info(req.getSession().getId()+" "+ userExistException.getMessage());
        return ReturnResultFactory.build(FAIL_CODE, userExistException.getMessage(),null);
    }

    @ExceptionHandler(SqlFailOperationException.class)
    @ResponseBody
    public ResponseDataSet<Object> exceptionHandler(HttpServletRequest req,SqlFailOperationException exception){
        log.info(req.getSession().getId()+" "+exception.getMessage());
        return ReturnResultFactory.build(FAIL_CODE,exception.getMessage(),null);
    }

    @ExceptionHandler(VideoFormatNotSupportException.class)
    @ResponseBody
    public ResponseDataSet<Object> exceptionHandler(HttpServletRequest req,VideoFormatNotSupportException exception){
        log.info(req.getSession().getId()+" "+exception.getMessage());
        return ReturnResultFactory.build(FAIL_CODE,exception.getMessage(),null);
    }

    @ExceptionHandler(FileFailOperationException.class)
    @ResponseBody
    public ResponseDataSet<Object> exceptionHandler(HttpServletRequest req,FileFailOperationException exception){
        log.info(req.getSession().getId()+" "+exception.getMessage());
        return ReturnResultFactory.build(FAIL_CODE,exception.getMessage(),null);
    }

    @ExceptionHandler(ImageFormatNotSupportException.class)
    @ResponseBody
    public ResponseDataSet<Object> exceptionHandler(HttpServletRequest req,ImageFormatNotSupportException exception){
        log.info(req.getSession().getId()+" "+exception.getMessage());
        return ReturnResultFactory.build(FAIL_CODE,exception.getMessage(),null);
    }

    @ExceptionHandler(DataFormatInvalidException.class)
    @ResponseBody
    public ResponseDataSet<Object> exceptionHandler(HttpServletRequest req,DataFormatInvalidException exception){
        log.info(req.getSession().getId()+" "+exception.getMessage());
        return ReturnResultFactory.build(FAIL_CODE,exception.getMessage(),null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseDataSet<Object> exceptionHandler(HttpServletRequest req,AccessDeniedException exception){
        log.info(req.getSession().getId()+" "+exception.getMessage());
        return ReturnResultFactory.build(FAIL_CODE,exception.getMessage(),null);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public String exceptionHandler(HttpServletRequest req,DataNotFoundException exception){
        log.info(req.getSession().getId()+" "+exception.getMessage());
        return "/error/404";
    }
}
