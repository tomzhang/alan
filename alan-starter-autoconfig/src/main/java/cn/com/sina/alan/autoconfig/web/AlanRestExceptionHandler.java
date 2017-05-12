package cn.com.sina.alan.autoconfig.web;

import cn.com.sina.alan.autoconfig.monitor.AlanFlowMonitorInterceptor;
import cn.com.sina.alan.autoconfig.monitor.AlanRequestCounter;
import cn.com.sina.alan.common.exception.AlanException;
import cn.com.sina.alan.common.http.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理控制器, 子类继承该类以简化异常处理流程
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 21/10/2016.
 */
//@ControllerAdvice
//@ConditionalOnProperty(prefix = "alan.auto", name = "exceptionHandler", havingValue = "true", matchIfMissing = true)
public class AlanRestExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AlanRestExceptionHandler.class);

    /**
     * 异常处理分发
     * @param req
     * @param resp
     * @param ex
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    protected ResponseResult handleException(HttpServletRequest req, HttpServletResponse resp, Throwable ex) {

        if (ex instanceof AlanException) {
            return handleAlanException((AlanException) ex, req, resp);
        }

        AlanRequestCounter.failedCount.incrementAndGet();
        //HttpHeaders headers = new HttpHeaders();
        if (ex instanceof org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleNoSuchRequestHandlingMethod((org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException) ex, status, req, resp);
        }
        else if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
            return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, status, req, resp);
        }
        else if (ex instanceof HttpMediaTypeNotSupportedException) {
            HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, status, req, resp);
        }
        else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
            return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, status, req, resp);
        }
        else if (ex instanceof MissingPathVariableException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleMissingPathVariable((MissingPathVariableException) ex, status, req, resp);
        }
        else if (ex instanceof MissingServletRequestParameterException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, status, req, resp);
        }
        else if (ex instanceof ServletRequestBindingException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleServletRequestBindingException((ServletRequestBindingException) ex, status, req, resp);
        }
        else if (ex instanceof ConversionNotSupportedException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleConversionNotSupported((ConversionNotSupportedException) ex, status, req, resp);
        }
        else if (ex instanceof TypeMismatchException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleTypeMismatch((TypeMismatchException) ex, status, req, resp);
        }
        else if (ex instanceof HttpMessageNotReadableException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, status, req, resp);
        }
        else if (ex instanceof HttpMessageNotWritableException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, status, req, resp);
        }
        else if (ex instanceof MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMethodArgumentNotValid((MethodArgumentNotValidException) ex, status, req, resp);
        }
        else if (ex instanceof MissingServletRequestPartException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMissingServletRequestPart((MissingServletRequestPartException) ex, status, req, resp);
        }
        else if (ex instanceof BindException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleBindException((BindException) ex, status, req, resp);
        }
        else if (ex instanceof NoHandlerFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleNoHandlerFoundException((NoHandlerFoundException) ex, status, req, resp);

        }
        else {
            //logger.warn("Unknown exception type: " + ex.getClass().getName());

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal((Exception) ex, status, req, resp);
        }

    }

    /**
     * 子类实现此方法处理业务异常
     * @param ex
     * @param request
     * @param response
     * @return
     */
    protected ResponseResult handleAlanException(AlanException ex, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseResult(ex.getCode(), ex.getMessage(), response);
    }

    /**
     * 所有异常都会经过该方法处理
     */
    protected ResponseResult handleExceptionInternal(Exception ex, HttpStatus status, HttpServletRequest request, HttpServletResponse resp) {
        log.warn("发生错误: {}, HTTP状态码:{}", ex, status);

        resp.setStatus(status.value());

        return new ResponseResult(status.value(), status.getReasonPhrase(), resp);
    }

    @Deprecated
    protected ResponseResult handleNoSuchRequestHandlingMethod(org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException ex,
                                                               HttpStatus status,
                                                               HttpServletRequest request,
                                                               HttpServletResponse resp) {

        //pageNotFoundLogger.warn(ex.getMessage());

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                 HttpStatus status,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse resp) {

        //pageNotFoundLogger.warn(ex.getMessage());

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                             HttpStatus status,
                                                             HttpServletRequest request,
                                                             HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                              HttpStatus status,
                                                              HttpServletRequest request,
                                                              HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleMissingPathVariable(MissingPathVariableException ex,
                                                       HttpStatus status,
                                                       HttpServletRequest request,
                                                       HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                  HttpStatus status,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                  HttpStatus status,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleConversionNotSupported(ConversionNotSupportedException ex,
                                                          HttpStatus status,
                                                          HttpServletRequest request,
                                                          HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleTypeMismatch(TypeMismatchException ex,
                                                HttpStatus status,
                                                HttpServletRequest request,
                                                HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                          HttpStatus status,
                                                          HttpServletRequest request,
                                                          HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                          HttpStatus status,
                                                          HttpServletRequest request,
                                                          HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpStatus status,
                                                          HttpServletRequest request,
                                                          HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                             HttpStatus status,
                                                             HttpServletRequest request,
                                                             HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleBindException(BindException ex,
                                                 HttpStatus status,
                                                 HttpServletRequest request,
                                                 HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }

    protected ResponseResult handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                           HttpStatus status,
                                                           HttpServletRequest request,
                                                           HttpServletResponse resp) {

        return handleExceptionInternal(ex, status, request, resp);
    }
}
