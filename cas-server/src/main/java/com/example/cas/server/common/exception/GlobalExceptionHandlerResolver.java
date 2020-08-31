package com.example.cas.server.common.exception;

import com.example.cas.server.common.result.Result;
import com.example.cas.server.common.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 异常处理器
 * @Author yempty
 * @Date 2020/5/14 9:16
 */
@RestControllerAdvice
public class GlobalExceptionHandlerResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerResolver.class);

    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleRenException(GlobalException ex) {
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handHttpMessageConversionException(HttpMessageConversionException ex) {
        return Result.error(ResultCode.INVALID_JSON_FORMAT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return Result.error();
    }

}
