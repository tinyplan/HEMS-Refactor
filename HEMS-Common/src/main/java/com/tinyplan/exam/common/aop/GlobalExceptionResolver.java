package com.tinyplan.exam.common.aop;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.tinyplan.exam.entity.pojo.ApiResult;
import com.tinyplan.exam.entity.pojo.BusinessException;
import com.tinyplan.exam.entity.pojo.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionResolver {
    public static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult<Object> handleException(Exception e){
        if (e instanceof TokenExpiredException) {
            LOGGER.error(ResultStatus.RES_ILLEGAL_REQUEST.getMessage());
            return new ApiResult<>(ResultStatus.RES_ILLEGAL_REQUEST, null);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            LOGGER.error(ResultStatus.RES_INVALID_REQUEST_TYPE.getMessage());
            return new ApiResult<>(ResultStatus.RES_INVALID_REQUEST_TYPE, null);
        }
        e.printStackTrace();
        return new ApiResult<>(ResultStatus.RES_UNKNOWN_ERROR, null);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiResult<Object> handleException(BusinessException e){
        LOGGER.error(e.getMsg());
        return new ApiResult<>(e.getStatus(), e.getMsg(), null);
    }

}
