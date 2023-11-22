package com.example.myspring.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.net.SocketTimeoutException;

/**
 * @author hugh
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ResponseBody
    @ExceptionHandler({AsyncRequestTimeoutException.class, SocketTimeoutException.class})
    public String asyncRequestTimeoutHandle(AsyncRequestTimeoutException exception) {
        log.warn("请求超时");
        return "304";
    }

}
