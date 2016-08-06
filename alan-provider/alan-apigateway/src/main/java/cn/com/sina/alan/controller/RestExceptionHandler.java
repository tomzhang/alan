package cn.com.sina.alan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 * Created by whf on 3/24/16.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    private String handleException(HttpServletRequest req, Throwable ex) {
        return "";
    }

    /**
     * 处理@RequestParam错误, 即参数不足
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("请求参数缺失: {}", ex.getMessage());

        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }


}
