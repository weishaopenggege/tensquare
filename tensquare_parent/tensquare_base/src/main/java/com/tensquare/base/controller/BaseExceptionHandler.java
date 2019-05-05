package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

/**
 * @author Wsp
 * @date 2019/5/5 20:56
 */
@ControllerAdvice
public class BaseExceptionHandler {

    static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){

        //打印日志
        //e.printStackTrace(); 不推荐
        logger.error("发生异常了", e);

        if (e instanceof NoSuchElementException) {
            return new Result(false, StatusCode.ERROR, "没找到数据");
        }
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
